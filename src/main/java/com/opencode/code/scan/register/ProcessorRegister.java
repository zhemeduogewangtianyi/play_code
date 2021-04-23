package com.opencode.code.scan.register;

import com.opencode.code.scan.service.Processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessorRegister {

    private static final Map<String, Processor> REGISTER_MAP = new ConcurrentHashMap<>();

    public static boolean register(String key , Processor processor){
        if(REGISTER_MAP.containsKey(key)){
            return false;
        }
        REGISTER_MAP.put(key,processor);
        return true;
    }

    public static boolean unregister(String key){
        if(!REGISTER_MAP.containsKey(key)){
            return false;
        }
        REGISTER_MAP.remove(key);
        return true;
    }

    public static Map<String,Processor> info(){
        return REGISTER_MAP;
    }

}
