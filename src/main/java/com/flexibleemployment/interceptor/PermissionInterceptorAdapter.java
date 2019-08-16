package com.flexibleemployment.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.flexibleemployment.helper.ShiroHelper;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.response.ResultVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 访问controller方法权限拦截器.
 *
 * @author daizhiyue
 * @version 1.0.0
 * @date 2017年8月9日
 */
public class PermissionInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionInterceptorAdapter.class);
    protected PatternMatcher pathMatcher = new AntPathMatcher();

    private List<String> excludeAuthPaths = new ArrayList<>();

    public void setExcludeAuthPaths(List<String> excludeAuthPaths) {
        this.excludeAuthPaths = excludeAuthPaths;
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

    protected String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (excludeAuthPath(request)) {
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String[] permissionArray = null;
            String[] roleArray = null;
            //整个controller类是否都忽略认证&授权
            Class<?> beanType = handlerMethod.getBeanType();
            if (beanType.isAnnotationPresent(AuthIgnore.class)) {
                return true;
            }
            if (handlerMethod.getMethod().isAnnotationPresent(AuthIgnore.class)) {
                return true;
            }
            if (handlerMethod.getMethod().isAnnotationPresent(RequiresPermissions.class)) {
                // 如果有设置注解，则以注解中的permission作为验证权限名称.
                RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
                permissionArray = requiresPermissions.value();
            }
            if (handlerMethod.getMethod().isAnnotationPresent(RequiresRoles.class)) {
                // 如果有设置注解，则以注解中的role作为验证权限名称.
                RequiresRoles requiresRoles = handlerMethod.getMethodAnnotation(RequiresRoles.class);
                roleArray = requiresRoles.value();
            }
            boolean isPermission = false;
            boolean isRole = false;
            Subject currentUser = ShiroHelper.getSubject(request, response);
            if (currentUser != null && currentUser.getPrincipal() != null) {
                // shiro鉴权
                //没有定义资源名称
                if (permissionArray == null && roleArray == null) {
                    return true;
                }
                if (permissionArray != null) {
                    for (String permission : permissionArray) {
                        if (currentUser.isPermitted(permission)) {
                            isPermission = true;
                            break;
                        }
                    }
                }
                if (roleArray != null) {
                    for (String role : roleArray) {
                        if (currentUser.hasRole(role)) {
                            isRole = true;
                            break;
                        }
                    }
                }
            }

            if (isPermission || isRole) {
                return true;
            } else {
                response.setContentType("text/html;charset=UTF8");

                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    ResultVO resultVO = ResultVO.validError("无权限操作!");
                    resultVO.setRespCode(ResultVO.NOPERMISSION_ERROR_CODE);
                    out.println(JSONObject.toJSON(resultVO));
                } catch (IOException e) {
                    LOGGER.error("Exception:", e);
                    e.printStackTrace();
                }

                out.flush();
                out.close();
                return false;
                // throw new AuthorizationException();
            }
        } else {
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
