package com.opencode.code.permission;

import com.opencode.code.permission.annotation.Permissionable;
import com.opencode.code.permission.bean.BuDO;
import com.opencode.code.permission.bean.BucUser;
import com.opencode.code.permission.enums.PermissionTypeEnum;
import com.opencode.code.permission.enums.UserPermissionEnum;
import com.opencode.code.permission.manager.BuManager;
import com.opencode.code.permission.manager.BucManager;
import com.opencode.code.permission.manager.PermissionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionInterceptor {

    @Autowired
    private BuManager buManager;

    @Autowired
    private BucManager bucManager;

    @Autowired
    private PermissionManager permissionManager;

    @Around(value = "@annotation(permissionable)")
    public Object permissionInterceptor(ProceedingJoinPoint pjp, Permissionable permissionable){

        try {

            UserPermissionEnum permissionAnnotation = permissionable.code();
            PermissionTypeEnum type = permissionable.type();

            String workNumber = "WTY";
            BucUser bucUser = bucManager.queryByNumber(workNumber);

            if(bucUser == null){
                return "租户不存在！";
            }

            String bu = bucUser.getBu();
            BuDO buDO = buManager.queryByName(bu);
            if(buDO == null){
                return "部门不存在！";
            }

            int buPermissionCode = buDO.getCode();
            boolean buPermission = permissionManager.checkPermission(buPermissionCode, permissionAnnotation);

            int userPermissionCode = bucUser.getUserPermission();
            boolean userPermission = permissionManager.checkPermission(userPermissionCode, permissionAnnotation);

            if(!userPermission || !buPermission){
                if(PermissionTypeEnum.DATA_QUERY.equals(type)){
                    return null;
                }
                if(!userPermission){
                    return "用户权限不足！";
                }else{
                    return "所在部门权限不足！";
                }

            }

            Object proceed = pjp.proceed();

            return proceed;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
//        return null;
    }

}
