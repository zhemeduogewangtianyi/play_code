package com.opencode.code.dubbo.spi.impl;

import com.opencode.code.dubbo.spi.interfaces.SimpleExt;
import org.apache.dubbo.common.URL;


public class SimpleExtImpl3 implements SimpleExt {


    @Override
    public String echo(URL url, String s) {
        return "Ext1Impl3-echo";
    }

    @Override
    public String yell(org.apache.dubbo.common.URL url, String s) {
        return "Ext1Impl3-yell";
    }

    @Override
    public String bang(org.apache.dubbo.common.URL url, int i) {
        return "Ext1Impl3-bang";
    }
}
