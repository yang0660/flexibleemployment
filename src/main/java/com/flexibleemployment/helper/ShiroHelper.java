package com.flexibleemployment.helper;

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
}
