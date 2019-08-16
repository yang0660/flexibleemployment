package com.flexibleemployment.shiro;

import java.lang.annotation.*;

/**
 * @author fyf
 * @date created in 20:41 2019/1/14
 * @description :
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {
    /*
    操作类型
    */
    public String operation() default "";

    /*
    操作模块
    */
    public String operationModule() default "";

    /*
    操作备注
    */
    public String remark() default "";

}
