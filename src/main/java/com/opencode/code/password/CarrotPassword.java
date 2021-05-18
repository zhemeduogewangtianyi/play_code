package com.opencode.code.password;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;

public class CarrotPassword {

    public static void main(String[] args) throws UnsupportedEncodingException {

        charsPassword();

        System.out.println();
        System.out.println();

        bytesPassword();

        String data = "{&quot;code&quot;:1}";
        JSONObject jsonObject = JSONObject.parseObject(StringEscapeUtils.unescapeHtml4(data));
        System.out.println(jsonObject);

    }

    private static void bytesPassword() throws UnsupportedEncodingException {
        int slat = 686;
        String ok = "这是一个非常牛逼的实验！";
        byte[] bytes = ok.getBytes("UTF-8");

        System.out.println(new String(bytes,"UTF-8"));

        for(int i = 0 ; i < bytes.length ; i++ ){
            bytes[i] ^= slat;
        }

        System.out.println(new String(bytes,"UTF-8"));

        for(int i = 0 ; i < bytes.length ; i++ ){
            bytes[i] ^= slat;
        }

        System.out.println(new String(bytes,"UTF-8"));

    }

    public static void charsPassword(){
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
