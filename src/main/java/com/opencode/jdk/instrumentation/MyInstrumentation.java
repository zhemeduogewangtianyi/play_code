package com.opencode.jdk.instrumentation;

import java.lang.instrument.Instrumentation;

public class MyInstrumentation {

    public static void premain(String arg, Instrumentation instrumentation) {
        System.err.println("agent startup , args is " + arg);
        // 注册我们的文件下载函数
        instrumentation.addTransformer(new DumpClassesService());
    }

}
