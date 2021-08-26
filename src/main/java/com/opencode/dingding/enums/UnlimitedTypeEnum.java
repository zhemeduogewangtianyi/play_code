package com.opencode.dingding.enums;

public enum UnlimitedTypeEnum {

    limit_21000("21000","上传文件提醒"),
    limit_164902("164902","未接来电提醒"),
    ;

    private final String value;
    private final String remark;

    UnlimitedTypeEnum(String value,String remark) {
        this.value = value;
        this.remark = remark;
    }

    public String getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
