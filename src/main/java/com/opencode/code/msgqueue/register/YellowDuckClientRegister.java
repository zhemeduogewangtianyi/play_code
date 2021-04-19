package com.opencode.code.msgqueue.register;

import com.opencode.code.msgqueue.client.YellowDuckClient;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class YellowDuckClientRegister {

    private static final Map<String, YellowDuckClient> CLIENT_REGISTER_MAP = new ConcurrentHashMap<>();

    public static synchronized boolean register(String name,String host,int port){
        YellowDuckClient yellowDuckClient ;
        try {
            yellowDuckClient = new YellowDuckClient(host,port,name);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if(StringUtils.isEmpty(name)){
            return false;
        }
        if(CLIENT_REGISTER_MAP.containsKey(name)){
            return false;
        }
        yellowDuckClient.start();
        CLIENT_REGISTER_MAP.put(name,yellowDuckClient);
        return true;
    }

    public static synchronized boolean unregister(String name){

        if(StringUtils.isEmpty(name)){
            return false;
        }
        if(!CLIENT_REGISTER_MAP.containsKey(name)){
            return false;
        }

        YellowDuckClient yellowDuckClient = CLIENT_REGISTER_MAP.get(name);
        boolean shutdown = yellowDuckClient.shutdown();
        return shutdown && CLIENT_REGISTER_MAP.remove(name) != null;
    }

    public static boolean send(String name,String msg){

        YellowDuckClient yellowDuckClient = CLIENT_REGISTER_MAP.get(name);
        if(yellowDuckClient == null){
            return false;
        }
        return yellowDuckClient.send(msg);
    }

}
