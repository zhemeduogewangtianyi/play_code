package com.opencode.code.log.interceptor.enums;

public enum LogTypeEnum {

    OPERATOR("0"), RESULT("1") , PARAM("2") ,ALL("3");

    private final String value;

    LogTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public String getValue() {
        return value;
    }
}
