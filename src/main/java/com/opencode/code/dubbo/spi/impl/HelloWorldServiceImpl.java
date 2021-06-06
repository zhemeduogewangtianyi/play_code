package com.opencode.code.dubbo.spi.impl;

import com.opencode.code.dubbo.spi.interfaces.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public void helloWorld() {
        System.out.println("Hello Dubbo SPI !");
    }

}
