package com.opencode.code.scheduler.ibf;

import java.util.ArrayDeque;

public class MockRedis {

    private static ArrayDeque<Object> deque = new ArrayDeque<>();

    public static void push(Object obj){
        deque.push(obj);
    }

    public static Object poll(){
        return deque.poll();
    }
}
