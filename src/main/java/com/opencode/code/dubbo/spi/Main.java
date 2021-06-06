package com.opencode.code.dubbo.spi;

import com.opencode.code.dubbo.spi.interfaces.HelloWorldService;
import org.apache.dubbo.common.extension.ExtensionLoader;

/** Dubbo SPI */
public class Main {

    public static void main(String[] args) {

        HelloWorldService helloWorldService = ExtensionLoader.getExtensionLoader(HelloWorldService.class).getDefaultExtension();
        helloWorldService.helloWorld();

    }

}
