package com.opencode.jni;

public class HelloJni {

    public native String hello();

    public static void main(String[] args) {
        System.loadLibrary("JniDll");
        String hello = new HelloJni().hello();
        System.out.println(hello);
    }

}
