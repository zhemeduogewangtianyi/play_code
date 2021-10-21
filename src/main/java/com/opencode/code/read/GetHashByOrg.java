package com.opencode.code.read;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetHashByOrg {

    public static void main(String[] args) throws IOException {

        String org = "9WQNcmBxwr";
        int i = org.hashCode() & 127;
        System.out.println(i);

        List<String> car1 = new ArrayList<>();

        File file = new File("/Users/wty/project/play_code/src/main/java/com/opencode/code/read/pic.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String data;
        while((data = br.readLine()) != null){
            car1.add(data);
        }

        br.close();

        List<String> car2 = new ArrayList<>();

        File file1 = new File("/Users/wty/project/play_code/src/main/java/com/opencode/code/read/pic1.txt");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
        String data1;
        while((data1 = br1.readLine()) != null){
            car2.add(data1);
        }

        br1.close();

        car1.removeAll(car2);

        System.out.println(car1);

        StringBuilder buff = new StringBuilder();
        for(String c : car1){
            buff.append("\"").append(c).append("\"").append(",").append("\n");
        }

        FileOutputStream fos = new FileOutputStream(new File("/Users/wty/project/play_code/src/main/java/com/opencode/code/read/pic2.txt"));
        fos.write(buff.toString().getBytes(StandardCharsets.UTF_8));
        fos.close();

    }

}
