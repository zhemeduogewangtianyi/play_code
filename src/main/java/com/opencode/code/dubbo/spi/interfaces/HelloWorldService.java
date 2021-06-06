package com.opencode.code.dubbo.spi.interfaces;

import org.apache.dubbo.common.extension.SPI;

@SPI("impl")
public interface HelloWorldService {

    void helloWorld();

}
