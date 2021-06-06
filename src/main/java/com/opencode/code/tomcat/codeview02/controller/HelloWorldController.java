package com.opencode.code.tomcat.codeview02.controller;

import com.opencode.code.tomcat.codeview02.anootation.CarrotGetMapping;

@CarrotGetMapping(value = "/hello")
public class HelloWorldController {

    @CarrotGetMapping(value = "hello")
    public Object hello(){
        return "hello";
    }

}
