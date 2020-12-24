package com.opencode.code.controller;

import com.opencode.code.signin.manager.SignInManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SignInController {

    @Autowired
    private SignInManager signInManager;

    @RequestMapping(value = "/signIn")
    public Object signIn(@RequestParam String type, @RequestParam String name) throws ParseException {
        return signInManager.signIn(type,name);
    }

}
