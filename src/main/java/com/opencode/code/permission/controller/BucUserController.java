package com.opencode.code.permission.controller;

import com.opencode.code.permission.bean.BuDO;
import com.opencode.code.permission.bean.BucUser;
import com.opencode.code.permission.manager.BuManager;
import com.opencode.code.permission.manager.BucManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/bucUser")
@RestController
public class BucUserController {

    @Autowired
    private BucManager bucManager;

    @Autowired
    private BuManager buManager;

    @RequestMapping(value = "/add")
    public Object add(@RequestParam String bu, @RequestParam String workNumber,@RequestParam Integer code){
        BucUser bucUser = new BucUser();


        BuDO buDO = buManager.queryByName(bu);
        if(buDO != null){

            int buPermission = buDO.getCode();
            bucUser.setBu(buDO.getName());
            bucUser.setNick(System.currentTimeMillis() + "");
            bucUser.setUsername(System.currentTimeMillis() + "");
            bucUser.setWorkNumber(workNumber);
            bucUser.setUserPermission(code);
            return bucManager.addUser(bucUser);
        }

        return false;
    }

    @RequestMapping(value = "/queryAll")
    public Object queryAll(){
        return bucManager.queryAll();
    }

    @RequestMapping(value = "/remove")
    public Object remove(@RequestParam String workNumber){
        return bucManager.remove(workNumber);
    }

    @RequestMapping(value = "/queryByNumber")
    public Object queryByCode(@RequestParam String workNumber){
        return bucManager.queryByNumber(workNumber);
    }

}
