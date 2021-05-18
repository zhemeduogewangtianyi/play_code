package com.opencode.code.password;

public class CarrotPassword {

    public static void main(String[] args) {

        int slat = 1000000000;

        String ok = "这是一个非常牛逼的实验！";
        char[] chars = ok.toCharArray();

        System.out.println(chars);

        for(int i = 0 ; i < chars.length ; i++){
            chars[i] = (char) (chars[i] ^ slat);
        }

        System.out.println(chars);

        for(int i = 0 ; i < chars.length ; i++){
            chars[i] = (char) (chars[i] ^ slat);
        }

        System.out.println(chars);

    }

}
