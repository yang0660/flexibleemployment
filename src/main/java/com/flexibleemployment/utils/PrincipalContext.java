package com.flexibleemployment.utils;

import com.flexibleemployment.shiro.UserAuthPrincipal;
import com.google.common.base.Preconditions;
import org.apache.shiro.SecurityUtils;

import java.io.Serializable;

/**
 * Created by zhangwanli on 2017/11/9.
 */
public class PrincipalContext {

    public static UserAuthPrincipal getUserPrincipal() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        //Preconditions.checkNotNull(principal, "user must be authenticated before call this method.");
        return (UserAuthPrincipal) principal;
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public static String getCurrentUserName() {
        UserAuthPrincipal userPrincipal = getUserPrincipal();
        return null == userPrincipal ? null : userPrincipal.getUserName();
    }

    public static String getSessionId() {
        Serializable sessionId = SecurityUtils.getSubject().getSession(false).getId();
        Preconditions.checkNotNull(sessionId, "user must be authenticated before call this method.");
        return sessionId.toString();
    }
}
