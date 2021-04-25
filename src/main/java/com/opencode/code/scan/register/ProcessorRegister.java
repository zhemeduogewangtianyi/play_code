package com.opencode.code.scan.register;

import com.opencode.code.scan.defination.ProcessorBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessorRegister {

    private static final Map<String, ProcessorBeanDefinition> REGISTER_MAP = new ConcurrentHashMap<>();
    private static final Map<Class, ProcessorBeanDefinition> REGISTER_CLASS_MAP = new ConcurrentHashMap<>();

    private static final Map<String, Object> BEAN_NAME_MAP = new ConcurrentHashMap<>();
    private static final Map<Class, Object> BEAN_CLASS_MAP = new ConcurrentHashMap<>();

    public static synchronized boolean register(String key , ProcessorBeanDefinition o){
        if(REGISTER_MAP.containsKey(key)){
            return false;
        }
        REGISTER_MAP.put(key,o);
        REGISTER_CLASS_MAP.put(o.getClz(),o);
        return true;
    }

    public static synchronized boolean unregister(String key){
        if(!REGISTER_MAP.containsKey(key)){
            return false;
        }
        Object o = REGISTER_MAP.get(key);
        REGISTER_MAP.remove(key);
        REGISTER_CLASS_MAP.remove(o.getClass());
        return true;
    }

    public static <T> T getBean(Class<T> clz) {
        ProcessorBeanDefinition beanDefinition = REGISTER_CLASS_MAP.get(clz);
        String name = beanDefinition.getName();
        Class aClass = beanDefinition.getClz();

        Object o = null;

        try {
            o = aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        if(BEAN_CLASS_MAP.containsKey(clz)){
           return (T) BEAN_CLASS_MAP.get(clz);
        }
        BEAN_NAME_MAP.put(name,o);
        BEAN_CLASS_MAP.put(aClass,o);

        return (T)o;
    }

    public static <T> T getBean(String key) {
        ProcessorBeanDefinition beanDefinition = REGISTER_MAP.get(key);
        String name = beanDefinition.getName();
        Class aClass = beanDefinition.getClz();

        Object o = null;

        try {
            o = aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        if(BEAN_NAME_MAP.containsKey(key)){
            return (T) BEAN_CLASS_MAP.get(key);
        }
        BEAN_NAME_MAP.put(name,o);
        BEAN_CLASS_MAP.put(aClass,o);

        return (T)o;
    }

    public static Map<String,ProcessorBeanDefinition> info(){
        return REGISTER_MAP;
    }

}
