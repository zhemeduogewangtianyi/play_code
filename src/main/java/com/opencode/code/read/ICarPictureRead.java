package com.opencode.code.read;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ICarPictureRead {

    public static void main(String[] args) throws IOException {

        String classpath = System.getProperty("user.dir") + "/" + "src/main/java/com/opencode/code/read/ICarPictureDubboServiceData.txt";
        System.out.println(classpath);

        FileInputStream fis = new FileInputStream(classpath);

        int len;
        byte[] data = new byte[1024];

        StringBuilder sb = new StringBuilder();
        while((len = fis.read(data)) != -1){
            sb.append(new String(data , 0 , len , "UTF-8"));
        }

        Document parse = Jsoup.parse(sb.toString());
        Set<String> strings = new HashSet<>(Arrays.asList(parse.select("#table_o > tbody > tr > td:nth-child(3)").text().split(" ")));
        for(String s : strings){
            System.out.println(s);
        }


    }

}
