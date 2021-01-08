package com.opencode.code.permission.controller;

import com.opencode.code.permission.bean.BuDO;
import com.opencode.code.permission.manager.BuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bu")
public class BuController {

    @Autowired
    private BuManager buManager;

    @RequestMapping(value = "/add")
    public Object add(@RequestParam String name, @RequestParam Integer code){
        BuDO buDO = new BuDO();
        buDO.setFlag(0);
        buDO.setName(name);
        buDO.setCode(code);
        return buManager.addBu(buDO);
    }

    @RequestMapping(value = "/queryAll")
    public Object queryAll(){
        return buManager.queryAll();
    }

    @RequestMapping(value = "/remove")
    public Object remove(@RequestParam String name){
        return buManager.remove(name);
    }

    @RequestMapping(value = "/queryByCode")
    public Object queryByCode(@RequestParam String name){
        return buManager.queryByName(name);
    }

}
