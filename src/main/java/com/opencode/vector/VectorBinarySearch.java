package com.opencode.vector;

import java.io.*;
import java.util.Scanner;

public class VectorBinarySearch {

    private static String[][] datas = new String[10][3];

    static {

        BufferedReader br = null;

        try {
            File file = new File("D:\\local-project\\play_code\\src\\main\\resources\\103976个英语单词库.txt");
            FileInputStream fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis));
            String data;
            int i = 0;

            while ((data = br.readLine()) != null) {
                String[] split = data.split("\t");
                for (int j = 0; j < 3; j++) {
                    datas[i][j] = split[j].replaceAll("\"", "");
                }
                i++;
                if (datas.length == i) {
                    String[][] temp = datas;
                    datas = new String[temp.length * 2][temp[0].length];
                    for (int outIdx = 0; outIdx < temp.length; outIdx++) {
                        for (int inIdx = 0; inIdx < temp[0].length; inIdx++) {
                            datas[outIdx][inIdx] = temp[outIdx][inIdx];
                        }
                    }
                }
            }
            String[][] tempDatas = new String[i][3];
            for (int index = 0; index < i; index++) {
                tempDatas[index] = datas[index];
            }
            datas = tempDatas;
            System.out.println("初始化成功！" + i + "个单词！");
        } catch (Exception e) {
            System.out.println("初始化失败！");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 启动标识，不许动 */
    static boolean start = false;

    /** 难度级别。。。 */
    private static int count = 3;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));

        String[][] source = new String[count][3];

        for (int i = 0; i < count; i++) {
            int random = (int) (Math.random() * datas.length);
            source[i] = datas[random];
            System.out.println(source[i][1] + "    " + source[i][2]);
        }

        String[][] success = new String[count][3];
        String[][] failed = new String[count][3];

        int successIdx = 0;
        int failedIdx = 0;

        int i = 0;

        while (true) {
            if(!start){
                System.out.println("准备好了？ Y/N");
            }
            String nextLine = scanner.nextLine();
            if (nextLine == null || nextLine.equals("")) {
                continue;
            }
            nextLine = nextLine.trim().toLowerCase();


            if(!start){
                if(nextLine.equals("y")){
                    start = true;
                    System.out.println(i + " " + source[i][2]);
                }
                continue;
            }

            String answer = source[i][1];
            boolean contains = answer.equals(nextLine);
            if (contains) {
                success[successIdx][0] = source[i][0];
                success[successIdx][1] = answer;
                success[successIdx][2] = source[i][2];
                System.out.println(source[i][2] + " 正确！");
                successIdx++;
            }else{
                failed[failedIdx][0] = source[i][0];
                failed[failedIdx][1] = answer;
                failed[failedIdx][2] = source[i][2];
                System.out.println(source[i][2] + " 错误！");
                failedIdx++;
            }

            i++;

            if(failedIdx + successIdx == count){
                if(failedIdx == 0){
                    System.out.println("本次结束 - 全部正确！");
                }else{
                    System.out.println("本次结束 - 错误的单词：");
                    for(String[] f : failed){
                        if(f[0] == null){
                            continue;
                        }
                        System.out.println(f[0] + " " + f[1] + " " + f[2]);
                    }
                }

                start = false;
                i = 0;
                successIdx = 0;
                failedIdx = 0;
                failed = new String[count][3];
                success = new String[count][3];

                System.out.println();
                System.out.println();
                System.out.println();

                for (int j = 0; j < count; j++) {
                    int random = (int) (Math.random() * datas.length);
                    source[j] = datas[random];
                    System.out.println(source[j][1] + "    " + source[j][2]);
                }

            }else{
                if(start){
                    System.out.println(i + " " + source[i][2]);
                }
            }
        }
    }

}
