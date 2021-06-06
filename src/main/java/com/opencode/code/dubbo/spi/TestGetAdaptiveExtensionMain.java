package com.opencode.code.dubbo.spi;

import com.opencode.code.dubbo.spi.interfaces.SimpleExt;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.HashMap;
import java.util.Map;

public class TestGetAdaptiveExtensionMain {

    public static void main(String[] args) throws Exception {

        testGetAdaptiveExtensionDefaultAdaptiveKet();

    }

    private static void testGetAdaptiveExtensionDefaultAdaptiveKet() throws Exception {

        ExtensionLoader<SimpleExt> extensionLoader = ExtensionLoader.getExtensionLoader(SimpleExt.class);
        SimpleExt adaptiveExtension = extensionLoader.getAdaptiveExtension();

        Map<String,String> map = new HashMap<>();
        URL url = new URL("p1","1.2.3.4",1010,"path1",map);

        String haha = adaptiveExtension.echo(url, "haha");

        System.out.println("Ext1Impl1-echo".equals(haha));

    }

}
