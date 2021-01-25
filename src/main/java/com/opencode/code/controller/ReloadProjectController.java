package com.opencode.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@RestController
@RequestMapping(value = "/reload")
public class ReloadProjectController {

    @Autowired
    private ConfigurableWebApplicationContext cw;

    @RequestMapping(value = "/reload")
    public Object reload(){
        cw.refresh();
        return true;
    }

}
