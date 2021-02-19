package com.opencode.code.zhizhu;

import java.io.*;

public class OperatorToDay {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("d://day.txt"))));
        String data ;
        while((data = br.readLine()) != null){
            System.out.println(data);
        }

    }

}
