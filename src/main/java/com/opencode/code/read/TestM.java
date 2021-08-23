package com.opencode.code.read;

public class TestM {

    public static void main(String[] args) {

        //Mac OS X
        String osName = System.getProperty("os.name");
        System.out.println(osName);

        String libraryPath = System.getProperty("java.library.path");
        System.out.println(libraryPath);

    }

}
