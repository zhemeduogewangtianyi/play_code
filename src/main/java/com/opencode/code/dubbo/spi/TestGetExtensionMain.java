package com.opencode.code.dubbo.spi;

import com.opencode.code.dubbo.spi.interfaces.HelloWorldService;
import org.apache.dubbo.common.extension.ExtensionLoader;

/** Dubbo SPI */
public class TestGetExtensionMain {

    public static void main(String[] args) {

        ExtensionLoader<HelloWorldService> extensionLoader = ExtensionLoader.getExtensionLoader(HelloWorldService.class);
        HelloWorldService helloWorldService = extensionLoader.getDefaultExtension();
        helloWorldService.helloWorld();

    }

}
