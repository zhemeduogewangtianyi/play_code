package com.opencode.code.log.interceptor.enums;

public enum LogSourceEnum {

    WEB("WEB"), RPC("RPC");

    private final String value;

    LogSourceEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public String getValue() {
        return value;
    }
}
