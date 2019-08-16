package com.flexibleemployment.shiro;

import com.alibaba.fastjson.JSON;
import com.flexibleemployment.enums.PassWordStatusEnum;
import com.flexibleemployment.utils.RequestUtil;
import com.flexibleemployment.vo.response.ResultVO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by zhuhecheng 2019-01-08
 */
public class CustomMobileAuthenticationFilter extends FormAuthenticationFilter implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(CustomMobileAuthenticationFilter.class);
    private static final String NOT_LOGIN_ERROR = "BSC10005";   //用户未登录
    private static final String PASSWORD_EXPIRE_ERROR = "BSC10006";   //密码已过期
    private static final String PASSWORD_RESET_ERROR = "BSC10007";   //密码已重置
    private static final String FILTER_NAME = CustomMobileAuthenticationFilter.class.getName();
    private static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";
    private static final Properties defaultStrategies;
    private final CorsProcessor corsProcessor = new DefaultCorsProcessor();
    private final UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

    private ApplicationContext context;
    private List<HandlerMapping> handlerMappings;
    private List<String> excludeAuthPaths = new ArrayList<>();

    /**
     * 是否是传统返回/渲染页面模式，如果是则可能需要在登录成功或失败后进行重定向
     * 否则就是app或者ajax方式请求，无需重定向处理，需要将控制权转给controller做业务处理，如封装登录返回信息
     */
    private boolean renderPageMode = false;
    private String updatePasswordUrl;

    static {
        try {
            ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
            defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + ex.getMessage());
        }
    }


    public void setExcludeAuthPaths(List<String> excludeAuthPaths) {
        this.excludeAuthPaths = excludeAuthPaths;
    }

    public void setRenderPageMode(boolean renderPageMode) {
        this.renderPageMode = renderPageMode;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.context = event.getApplicationContext();
        initStrategies();
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return excludeAuthPath(request) || super.onPreHandle(request, response, mappedValue);
    }

    private boolean excludeAuthPath(ServletRequest request) {
        String uri = getPathWithinApplication(request);
        for (String excludeAuthPath : excludeAuthPaths) {
            if (pathMatcher.matches(excludeAuthPath, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (super.isAccessAllowed(request, response, mappedValue)) {
            PassWordStatusEnum passWordStatusEnum = getPassWordStatusEnum();
            return passWordStatusEnum == null || passWordStatusEnum == PassWordStatusEnum.EFFECTIVE || pathsMatch(updatePasswordUrl, request);
        }
        return false;
    }

    protected PassWordStatusEnum getPassWordStatusEnum() {
        Session session = SecurityUtils.getSubject().getSession(false);
        if (session == null) {
            return null;
        }

        String passwordStatus = (String) session.getAttribute("passwordStatus");
        if (passwordStatus == null) {
            return null;
        } else {
            return PassWordStatusEnum.valueOf(passwordStatus);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (renderPageMode) {
            return super.onAccessDenied(servletRequest, servletResponse);
        } else {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Pair<HandlerExecutionChain, HandlerMapping> pair;
            try {
                pair = getHandlerPair(request);
            } catch (Exception e) {
                return false;
            }
            Object handler = pair.getLeft().getHandler();
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                //整个controller类是否都忽略认证&授权
                Class<?> beanType = handlerMethod.getBeanType();
                if (beanType.isAnnotationPresent(AuthIgnore.class)) {
                    return true;
                }
                //当前请求方法是否都忽略认证&授权
                Method method = handlerMethod.getMethod();
                if (method.isAnnotationPresent(AuthIgnore.class)) {
                    return true;
                }
                logger.warn("用户未登录:{}", RequestUtil.traceRequest(request));
                processCors((RequestMappingHandlerMapping) pair.getRight(), handler, request, response);

                ResultVO error;
                PassWordStatusEnum passWordStatusEnum = getPassWordStatusEnum();
                if (passWordStatusEnum != null && passWordStatusEnum != PassWordStatusEnum.EFFECTIVE) {
                    switch (passWordStatusEnum) {
                        case EXPIRE:
                            error = ResultVO.validError("密码已过期，请前往修改");
                            break;
                        case RESET:
                            error = ResultVO.validError("管理已重置密码，请前往修改");
                            break;
                        default:
                            error = ResultVO.validError("密码状态错误");
                    }
                } else {
                    error = ResultVO.validError("用户未登录");
                    error.setRespCode(ResultVO.UNLOGIN_ERROR_CODE);
                }
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().print(JSON.toJSONString(error));
                return false;
            }
            return true;
        }
    }

    private void processCors(RequestMappingHandlerMapping handlerMapping, Object handler, HttpServletRequest request, HttpServletResponse response) throws IOException {
        CorsConfiguration corsConfig = getCorsConfiguration();
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        if (CorsUtils.isCorsRequest(request)) {
            if (corsConfigSource.getCorsConfigurations().isEmpty()) {
                //Set "global" CORS configuration
                corsConfigSource.setCorsConfigurations(handlerMapping.getCorsConfigurations());
            }
            corsProcessor.processRequest(corsConfig, request, response);
        }
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return !renderPageMode || super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return super.onLoginFailure(token, e, request, response);
    }

    private Pair<HandlerExecutionChain, HandlerMapping> getHandlerPair(HttpServletRequest request) throws Exception {
        for (HandlerMapping hm : this.handlerMappings) {
            HandlerExecutionChain handler = hm.getHandler(request);
            if (handler != null) {
                return new ImmutablePair<>(handler, hm);
            }
        }
        throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), new ServletServerHttpRequest(request).getHeaders());
    }

    private void initStrategies() {
        initHandlerMappings();
    }

    private void initHandlerMappings() {
        this.handlerMappings = null;
        Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
        if (!matchingBeans.isEmpty()) {
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
            AnnotationAwareOrderComparator.sort(this.handlerMappings);
        }
        if (this.handlerMappings == null) {
            this.handlerMappings = getDefaultStrategies(HandlerMapping.class);
            if (logger.isDebugEnabled()) {
                logger.debug("No HandlerMappings found in servlet '" + FILTER_NAME + "': using default");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getDefaultStrategies(Class<T> strategyInterface) {
        String key = strategyInterface.getName();
        String value = defaultStrategies.getProperty(key);
        if (value != null) {
            String[] classNames = StringUtils.commaDelimitedListToStringArray(value);
            List<T> strategies = new ArrayList<>(classNames.length);
            for (String className : classNames) {
                try {
                    Class<?> clazz = ClassUtils.forName(className, DispatcherServlet.class.getClassLoader());
                    Object strategy = createDefaultStrategy(clazz);
                    strategies.add((T) strategy);
                } catch (ClassNotFoundException ex) {
                    throw new BeanInitializationException(
                            "Could not find DispatcherServlet's default strategy class [" + className + "] for interface [" + key + "]", ex);
                } catch (LinkageError err) {
                    throw new BeanInitializationException(
                            "Error loading DispatcherServlet's default strategy class [" + className + "] for interface [" + key + "]: problem with class file or dependent class", err);
                }
            }
            return strategies;
        } else {
            return new LinkedList<>();
        }
    }

    private Object createDefaultStrategy(Class<?> clazz) {
        return context.getAutowireCapableBeanFactory().createBean(clazz);
    }

    public void setUpdatePasswordUrl(String updatePasswordUrl) {
        this.updatePasswordUrl = updatePasswordUrl;
    }
}
