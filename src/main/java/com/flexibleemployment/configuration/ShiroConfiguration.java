package com.flexibleemployment.configuration;

import com.flexibleemployment.shiro.CustomMobileAuthenticationFilter;
import com.flexibleemployment.shiro.CustomWebSessionManager;
import com.flexibleemployment.shiro.ManageRealm;
import com.flexibleemployment.shiro.WxHashedCredentialsMatcher;
import com.google.common.collect.Lists;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.AuthorizingSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwanli on 2017/4/12.
 */
@Configuration
public class ShiroConfiguration implements ApplicationContextAware {

    public static final String HASH_ALGORITH_NAME = "md5";
    public static final int HASH_ITERATIONS = 3;
    public static final int SESSION_TIMEOUT_HOURS = 24 * 90;

    private ApplicationContext applicationContext;
    private List<String> excludeAuthPaths = Lists.newArrayList(
            "/swagger-resources/**",
            "/v2/api-docs",
            "/webjars/**",
            "/static/**",
            "/public/**",
            "/templates/**"
    );

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizationAttributeSourceAdvisor.class)
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");//该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetBeanName", "shiroFilter");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean("shiroFilter")
    @ConditionalOnMissingBean(ShiroFilterFactoryBean.class)
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        HashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", authenticatingFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticatingFilter.class)
    public AuthenticatingFilter authenticatingFilter() {
        CustomMobileAuthenticationFilter authenticationFilter = new CustomMobileAuthenticationFilter();
        authenticationFilter.setExcludeAuthPaths(excludeAuthPaths);
        return authenticationFilter;
    }

    private Map<String, String> loadFilterChainDefinitionMap() {
        HashMap<String, String> map = new LinkedHashMap<>();
        excludeAuthPaths.forEach(excludeAuthPath -> map.put(excludeAuthPath, "anon"));
        map.put("/**", "authc");
        return map;
    }

    @Bean
    @ConditionalOnMissingBean(CredentialsMatcher.class)
    public WxHashedCredentialsMatcher hashedCredentialsMatcher() {
        WxHashedCredentialsMatcher credentialsMatcher = new WxHashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(HASH_ALGORITH_NAME);
        credentialsMatcher.setHashIterations(HASH_ITERATIONS);
        return credentialsMatcher;
    }

    @Bean
    public AuthorizingRealm manageAuthorizingRealm() {
        ManageRealm realm = new ManageRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizingSecurityManager.class)
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(applicationContext.getBeansOfType(Realm.class).values());
        SessionManager sessionManager = sessionManager();
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    @ConditionalOnMissingBean(DefaultSessionManager.class)
    public SessionManager sessionManager() {
        CustomWebSessionManager sessionManager = new CustomWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(true);
        sessionManager.setGlobalSessionTimeout(Duration.ofHours(SESSION_TIMEOUT_HOURS).getSeconds() * 1000);
        return sessionManager;
    }
}
