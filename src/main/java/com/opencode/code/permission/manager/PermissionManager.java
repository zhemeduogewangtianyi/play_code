package com.opencode.code.permission.manager;

import com.opencode.code.permission.enums.UserPermissionEnum;
import org.springframework.stereotype.Component;

@Component
public class PermissionManager {

    /**
     * 校验权限
     * @author    WTY
     * @date    2020/12/31 15:53
     * @param code 现有权限码
     * @param permissionEnum 需要对比的权限
     * @return  boolean
     */
    public boolean checkPermission(int code, UserPermissionEnum permissionEnum){
        return (permissionEnum.getCode() & (~code)) == 0;
    }

    /**
     *
     * @author    WTY
     * @date    2020/12/31 15:53
     * @param code 当前拥有的权限权值
     * @param permissionEnums 想要赋予的权限权值们
     * @return  int 新的权值
     */
    public int addPermission(int code, UserPermissionEnum... permissionEnums){
        int item = code;
        for(UserPermissionEnum permissionEnum : permissionEnums){
            item |= permissionEnum.getCode();
        }
        return item;
    }

    /**
     * 去除权限
     * @author    WTY
     * @date    2020/12/31 15:54
     * @param code 当前拥有的权限权值
     * @param permissionEnums 想要提出的权限权值们
     * @return  int 返回修改后的新权值
     */
    public int delPermission(int code, UserPermissionEnum... permissionEnums){
        int item = code;
        for(UserPermissionEnum permissionEnum : permissionEnums){
            item &= (~permissionEnum.getCode());
        }
        return item;
    }


    public static void main(String[] args) {

        PermissionManager userPermissionManager = new PermissionManager();

        int permission = 1;

        permission = userPermissionManager.addPermission(permission, UserPermissionEnum.ADD, UserPermissionEnum.DEL, UserPermissionEnum.UPD);
        System.out.println(permission);

        permission = userPermissionManager.delPermission(permission, UserPermissionEnum.ADD, UserPermissionEnum.DEL);
        System.out.println(permission);

        boolean b = userPermissionManager.checkPermission(permission, UserPermissionEnum.UPD);
        boolean b1 = userPermissionManager.checkPermission(permission, UserPermissionEnum.DEL);
        System.out.println(b + "       " + b1);

    }

}
