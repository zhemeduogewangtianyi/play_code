package com.opencode.code.listeners.change2.mock;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;

public class Redis {

    private static LinkedHashMap<String, ArrayDeque<Object>> map = new LinkedHashMap<>();

    public static synchronized void push(String key ,Object value){
        ArrayDeque<Object> deque = map.get(key);
        if(deque == null){
            deque = new ArrayDeque<>();
        }
        deque.push(value);
        map.put(key,deque);
    }

    public static synchronized Object poll(String key){
        ArrayDeque<Object> deque = map.get(key);
        try{
            return deque.pop();
        }catch(Exception e){
            return null;
        }

    }

}
