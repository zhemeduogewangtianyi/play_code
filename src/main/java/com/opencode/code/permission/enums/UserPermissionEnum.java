package com.opencode.code.permission.enums;

/**
 * 权限枚举
 * @author    WTY
 * @date    2020/12/31 15:32
 */
public enum UserPermissionEnum {

    ADD(1,"增加权限"),
    UPD(2,"修改权限"),
    SEL(4,"查看权限"),
    DEL(8,"删除权限"),
    OPE(16,"功能按钮权限"),

    ;

    private final Integer code;

    private final String name;

    UserPermissionEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static UserPermissionEnum getByCode(Integer code){
        for(UserPermissionEnum userPermissionEnum : UserPermissionEnum.values()){
            if(userPermissionEnum.getCode().equals(code)){
                return userPermissionEnum;
            }
        }
        throw new IllegalArgumentException("not found code");
    }
}
