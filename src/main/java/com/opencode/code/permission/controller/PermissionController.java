package com.opencode.code.permission.controller;

import com.opencode.code.permission.bean.BucUser;
import com.opencode.code.permission.enums.UserPermissionEnum;
import com.opencode.code.permission.manager.BucManager;
import com.opencode.code.permission.manager.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionManager userPermissionManager;

    @Autowired
    private BucManager bucManager;

    @RequestMapping(value = "/queryAllUserPermission")
    public Object queryAllUserPermission(){
        return UserPermissionEnum.values();
    }

    @RequestMapping(value = "/addUserPermission")
    public Object addUserPermission(Integer code,String workNumber){
        BucUser bucUser = bucManager.queryByNumber(workNumber);
        if(bucUser == null){
            return "用户不存在";
        }
        int userPermission = bucUser.getUserPermission();
        UserPermissionEnum byCode = UserPermissionEnum.getByCode(code);
        int newPermission = userPermissionManager.addPermission(userPermission, byCode);
        bucUser.setUserPermission(newPermission);
        return bucManager.addUser(bucUser);
    }

    @RequestMapping(value = "/removeUserPermission")
    public Object removeUserPermission(Integer code,String workNumber){
        BucUser bucUser = bucManager.queryByNumber(workNumber);
        if(bucUser == null){
            return "用户不存在";
        }
        int userPermission = bucUser.getUserPermission();
        UserPermissionEnum byCode = UserPermissionEnum.getByCode(code);
        int newPermission = userPermissionManager.delPermission(userPermission, byCode);
        bucUser.setUserPermission(newPermission);
        return bucManager.addUser(bucUser);

    }

}
