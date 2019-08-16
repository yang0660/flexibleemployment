package com.flexibleemployment.shiro;

import java.lang.annotation.*;

/**
 * 忽略认证、授权
 * Created by zhuhecheng 2019-01-08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface AuthIgnore {

}
