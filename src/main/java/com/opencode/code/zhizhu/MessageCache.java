package com.opencode.code.zhizhu;

import org.springframework.util.CollectionUtils;

import java.util.LinkedList;

public class MessageCache {

    private static LinkedList<String> queue = new LinkedList<>();

    public static void push(String msg){
        queue.push(msg);
    }

    public static String pop(){
        if(CollectionUtils.isEmpty(queue)){
            return null;
        }
        return queue.pop();
    }

}
