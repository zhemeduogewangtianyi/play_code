package com.opencode.suanfa;

import com.alibaba.fastjson.JSON;

public class RotateQueue {

    //Redo log
    public static void main(String[] args){
        Object[] data = new Object[10];
        int len = data.length;
        int front = 0;
        for(int i = 0 ; i < 1000 ; i ++){
            front = i % len;
            data[front] = i;
            System.out.println(JSON.toJSONString(data));

        }
    }

}
