package com.opencode.code.log.interceptor.annotation;



import com.opencode.code.log.interceptor.enums.LogSourceEnum;

import java.lang.annotation.*;

/**
 * log 注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {

    /** 标题 */
    String title() default "";

    /** 类型 */
    int type();

    /**
     * 日志信息描述,可以记录该方法的作用等信息。
     */
    String descp() default "";

    /**
     * 日志来源，比如dubbo接口，web接口
     */
    LogSourceEnum source() default LogSourceEnum.WEB;

    /**
     * 入参输出范围，值为入参变量名，多个则逗号分割。不为空时，入参日志仅打印include中的变量
     */
    String include() default "";

    /**
     * 是否存入数据库
     */
    boolean db() default true;

}
