package com.opencode.code.msgqueue.register;

import com.opencode.code.msgqueue.server.ServerThread;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class YellowDuckServerRegister {

    private static final Map<String, ServerThread> SERVER_REGISTER_MAP = new ConcurrentHashMap<>();

    public static synchronized boolean register(String name,ServerThread st){

        if(StringUtils.isEmpty(name)){
            return false;
        }
        if(SERVER_REGISTER_MAP.containsKey(name)){
            return false;
        }
        st.start();
        SERVER_REGISTER_MAP.put(name,st);
        return true;
    }

    public static synchronized boolean unregister(String name){

        if(StringUtils.isEmpty(name)){
            return false;
        }
        if(!SERVER_REGISTER_MAP.containsKey(name)){
            return false;
        }

        ServerThread st = SERVER_REGISTER_MAP.get(name);
        boolean shutdown = st.shutdown();
        return shutdown && SERVER_REGISTER_MAP.remove(name) != null;
    }

    public static boolean send(String name,String msg){

        for(Iterator<Map.Entry<String, ServerThread>> car = SERVER_REGISTER_MAP.entrySet().iterator() ; car.hasNext();){
            Map.Entry<String, ServerThread> next = car.next();
            String key = next.getKey();
            ServerThread value = next.getValue();
            if(key.equals(name)){
                continue;
            }
            boolean send = value.send(msg);
            if(!send){
                return false;
            }
        }
        return true;
    }

}
