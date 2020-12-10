package com.opencode.code.log.interceptor.annotation;



import com.opencode.code.log.interceptor.enums.LogScopeEnum;
import com.opencode.code.log.interceptor.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * log 注解 - 上下文思想
 * @author    WTY
 * @date    2020/06/19 19:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {

    /**
     * 是否输出日志
     */
    boolean loggable() default true;

    /**
     * 日志信息描述,可以记录该方法的作用等信息。
     */
    String descp() default "";

    /**
     * 日志类型，可能存在多种接口类型都需要记录日志，比如dubbo接口，web接口
     */
    LogTypeEnum type() default LogTypeEnum.OPERATOR;

    /**
     * 日志输出范围,用于标记需要记录的日志信息范围，包含入参、返回值等。
     * ALL-入参和出参, BEFORE-入参, AFTER-出参
     */
    LogScopeEnum scope() default LogScopeEnum.ALL;

    /**
     * 入参输出范围，值为入参变量名，多个则逗号分割。不为空时，入参日志仅打印include中的变量
     */
    String include() default "";

    /**
     * 是否存入数据库
     */
    boolean db() default true;

}
