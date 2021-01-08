package com.opencode.code.permission.enums;

public enum  PermissionTypeEnum {

    DATA_QUERY(0,"数据查看权限"),
    DATA_OPERATION(1,"数据操作权限"),

    ;

    private int code;

    private String name;

    PermissionTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
