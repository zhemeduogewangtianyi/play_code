package com.opencode.mockmq;

import java.util.ArrayList;

public class MqQueue {

    public static final ArrayList<String> queue = new ArrayList<>();

    public synchronized static void send(String value){
        queue.add(value);
    }

    public synchronized static String receiver(int index){
        try{
            return queue.get(index);
        }catch(Exception e){
            return null;
        }
    }

}
