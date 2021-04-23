package com.opencode.code.scan;

import com.opencode.code.scan.service.impl.BaseProcessor;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("111");
        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = classLoader.getResource("");
        String filePath = resource.getFile();

//        findFile(filePath,classLoader);

        System.out.println();
    }



}
