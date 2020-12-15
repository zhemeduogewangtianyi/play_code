package com.opencode.jni;

public class Enter {

    public static void main(String[] args) {
        System.loadLibrary("JniDll");
        String hello = new HelloJni().hello();
        System.out.println(hello);
    }

}
