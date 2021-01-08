package com.opencode.code.permission.annotation;

import com.opencode.code.permission.enums.PermissionTypeEnum;
import com.opencode.code.permission.enums.UserPermissionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissionable {

    /** 0:数据查看权限 1：数据操作权限 */
    PermissionTypeEnum type();

    /** 用户权限码 默认没有任何权限 */
    UserPermissionEnum code() ;

}
