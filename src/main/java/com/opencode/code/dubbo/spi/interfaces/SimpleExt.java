package com.opencode.code.dubbo.spi.interfaces;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;


/**
 * 测试 @Adaptive
 * */
@SPI("impl1")
public interface SimpleExt {

    @Adaptive
    String echo(URL url, String s);

    @Adaptive({"key1", "key2"})
    String yell(org.apache.dubbo.common.URL url, String s);

    // no @Adaptive
    String bang(org.apache.dubbo.common.URL url, int i);

}
