package com.opencode.vector;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VectorBinarySearch {

    public static void main(String[] args) throws IOException {

//        List list = new ArrayList<>();
//
//        for(char c = '\u4e00' ; c < '\u9fa5' ; c++){
//            System.out.println(c);
//            list.add(c);
//        }
//
//        System.out.println(list);

        String[][] datas = {};
        File file = new File("D:\\local-project\\play_code\\src\\main\\resources\\103976个英语单词库.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String data;
        int i = 0 ;
        int j = 0;
        while((data = br.readLine()) != null){
            String[] split = data.split("\t");
            char[] chars = split[2].toCharArray();
//            for(chars)
            System.out.println();
        }

        br.close();


    }

}
