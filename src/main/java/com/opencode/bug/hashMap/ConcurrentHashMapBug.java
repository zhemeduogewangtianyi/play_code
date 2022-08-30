package com.opencode.bug.hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapBug {

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(16);
//        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(16);
        map.computeIfAbsent("Aa", new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return map.computeIfAbsent("BB", key2 -> 42);
            }
        });
        System.out.println("Aa".hashCode());//2112
        System.out.println("BB".hashCode());//2112


        System.out.println(map);
    }

}
