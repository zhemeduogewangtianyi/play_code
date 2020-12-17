package com.opencode.code.log.interceptor.result;

import lombok.Data;

@Data
public class LogResult {

    /** 标题 */
    private String title;

    /** 类型 */
    private Integer type;

    /** 类名称 */
    private String className;

    /** 方法名称 */
    private String methodName;

    /** 入参 */
    private String params;

    /** 返回 */
    private String result;

    /** 描述信息 */
    private String descp;

    /** 日志来源 */
    private String logSource;

    /** 当前登录人 */
    private String sysUser;

}
