package com.opencode.code.log.interceptor.enums;

public enum LogScopeEnum {

    ALL, BEFORE, AFTER;

    public boolean contains(LogScopeEnum scope) {
        if (this == ALL) {
            return true;
        } else {
            return this == scope;
        }
    }

    @Override
    public String toString() {
        String str = "";
        switch (this) {
            case ALL:
                break;
            case BEFORE:
                str = "REQUEST";
                break;
            case AFTER:
                str = "RESPONSE";
                break;
            default:
                break;
        }
        return str;
    }

}
