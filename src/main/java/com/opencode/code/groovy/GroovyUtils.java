package com.opencode.code.groovy;


import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * groovy runtime
 * */
public class GroovyUtils {

    private static final Map<String, GroovyObject> SCRIPT_MAP = new ConcurrentHashMap<>();

    public static Object evalScript(String script,String methodName,Object[] args) throws IllegalAccessException, InstantiationException {
        String cacheKey = DigestUtils.md5Hex(script);
        GroovyObject gObj;
        if(SCRIPT_MAP.containsKey(cacheKey)){
            gObj = SCRIPT_MAP.get(cacheKey);
        }else{
            Class<?> cls = new GroovyClassLoader().parseClass(script);
            gObj = (GroovyObject)cls.newInstance();
        }
        return gObj.invokeMethod(methodName, args);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        String script = "class Test {\n" +
                "\n" +
                "    def solution(String name,String age){\n" +
                "        List<Map<String,Object>> list = []\n" +
                "        Map<String,Object> map = [:]\n" +
                "        map.put(\"name\",name)\n" +
                "        map.put(\"age\",age)\n" +
                "        list.add(map)\n" +
                "        return list\n" +
                "    }\n" +
                "\n" +
                "}";
//        Object[] arg = {"dddxhh","18"};
//        Object solution = GroovyUtils.evalScript(script, "solution", arg);
//        System.out.println(solution);

        String cacheKey = DigestUtils.md5Hex("script");
        String cacheKey1 = DigestUtils.md5Hex("script1");
        System.out.println(cacheKey.equals(cacheKey1));


    }

}
