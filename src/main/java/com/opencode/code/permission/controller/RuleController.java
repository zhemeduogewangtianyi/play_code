package com.opencode.code.permission.controller;

import com.opencode.code.permission.annotation.Permissionable;
import com.opencode.code.permission.bean.RuleDO;
import com.opencode.code.permission.enums.PermissionTypeEnum;
import com.opencode.code.permission.enums.UserPermissionEnum;
import com.opencode.code.permission.manager.RuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/model")
@RestController
public class RuleController {

    @Autowired
    private RuleManager ruleManager;

    @Permissionable(type = PermissionTypeEnum.DATA_OPERATION,code = UserPermissionEnum.ADD)
    @RequestMapping(value = "/add")
    public Object add(@RequestParam String name, @RequestParam Integer code){
        try{
            RuleDO ruleDO = new RuleDO();
            ruleDO.setFlag(0);
            ruleDO.setName(name);
            ruleDO.setCode(code);
            return ruleManager.addPermission(ruleDO);
        }catch(Exception e){
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/queryAll")
    public Object queryAll(){
        return ruleManager.queryAll();
    }

    @RequestMapping(value = "/remove")
    public Object remove(Long id){
        return ruleManager.remove(id);
    }

    @RequestMapping(value = "/queryByCode")
    public Object queryByCode(@RequestParam Integer code){
        return ruleManager.queryByCode(code);
    }

}
