package com.flexibleemployment.helper;

import com.flexibleemployment.shiro.ManageUserNamePasswordToken;
import com.flexibleemployment.shiro.UserAuthPrincipal;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShiroHelper
 *
 * @author daizhiyue
 * @version 1.0.0
 * @date 2017年8月16日
 */
public class ShiroHelper {

    public static Subject getSubject(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = ThreadContext.getSubject();
        if (subject == null) {
            subject = new WebSubject.Builder(request, response).buildSubject();
            ThreadContext.bind(subject);
        }
        return subject;
    }

    public static String getSessionOpenId(){
        Subject subject = ThreadContext.getSubject();
        System.out.println(subject.getPrincipal().getClass());
        UserAuthPrincipal userAuthPrincipal = (UserAuthPrincipal) subject.getPrincipal();
        return userAuthPrincipal.getPrincipal();
    }
}
