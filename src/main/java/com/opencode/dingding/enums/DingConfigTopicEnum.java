package com.opencode.dingding.enums;

public enum DingConfigTopicEnum {

    OA_USER("oa_user"),
    ORG_SCREEN("org_screen"),
    CH_USER("ch_user"),
    ;

    private final String value;

    DingConfigTopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
