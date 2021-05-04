package com.opencode.code.controller;

import com.opencode.jni.HelloJni;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jni")
public class JniController {

//    static{
//        System.loadLibrary("JniDll");
//    }

    @RequestMapping(value = "/testJni")
    public Object testJni(){

        return  new HelloJni().hello();
    }

}
