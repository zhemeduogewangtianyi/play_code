package com.opencode.code.msgqueue.register;

import com.opencode.code.msgqueue.client.YellowDuckClient;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class YellowDuckRegister {

    private static final Map<String, YellowDuckClient> REGISTER_MAP = new ConcurrentHashMap<>();

    public static synchronized boolean register(String name,String host,int port){
        YellowDuckClient yellowDuckClient = null;
        try {
            yellowDuckClient = new YellowDuckClient(host,port,name);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if(StringUtils.isEmpty(name)){
            return false;
        }
        if(REGISTER_MAP.containsKey(name)){
            return false;
        }
        yellowDuckClient.start();
        REGISTER_MAP.put(name,yellowDuckClient);
        return true;
    }

    public static synchronized boolean unregister(String name){

        if(StringUtils.isEmpty(name)){
            return false;
        }
        if(!REGISTER_MAP.containsKey(name)){
            return false;
        }

        YellowDuckClient yellowDuckClient = REGISTER_MAP.get(name);
        boolean shutdown = yellowDuckClient.shutdown();
        return shutdown && REGISTER_MAP.remove(name) != null;
    }

    public static synchronized boolean send(String name,String msg){

        YellowDuckClient yellowDuckClient = REGISTER_MAP.get(name);
        if(yellowDuckClient == null){
            return false;
        }
        return yellowDuckClient.send(msg);
    }

}
