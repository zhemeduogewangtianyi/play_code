package com.opencode.code.signin.cache;

import com.opencode.code.signin.bean.SignInContext;

import java.util.HashMap;
import java.util.Map;

/** 签到缓存结构 */
public class SignInCacheStructure {

    private static final Map<String,Map<String,Map<String, Map<String,Integer>>>> TYPE_MAP = new HashMap<>();

    public static boolean cacheCount(SignInContext signInContext){

        String type = signInContext.getType();
        String username = signInContext.getUsername();
        String year = signInContext.getYear();
        String month = signInContext.getMonth();

        Map<String, Map<String, Map<String, Integer>>> userNameMap = TYPE_MAP.get(type);
        if(userNameMap == null){
            userNameMap = new HashMap<>();
        }
        Map<String, Map<String, Integer>> yearMap = userNameMap.get(username);
        if(yearMap == null){
            yearMap = new HashMap<>();
        }
        Map<String, Integer> monthMap = yearMap.get(year);
        if(monthMap == null){
            monthMap = new HashMap<>();
        }
        Integer count = signInContext.getCount();
        if(count == null){
            count = 0;
        }
        monthMap.put(month,count);
        yearMap.put(year,monthMap);
        userNameMap.put(username,yearMap);
        TYPE_MAP.put(type,userNameMap);
        return true;
    }

    public static Integer getCacheCount(SignInContext signInContext){

        String type = signInContext.getType();
        String username = signInContext.getUsername();
        String year = signInContext.getYear();
        String month = signInContext.getMonth();

        Map<String, Map<String, Map<String, Integer>>> userNameMap = TYPE_MAP.get(type);
        if(userNameMap == null){
            userNameMap = new HashMap<>();
        }
        Map<String, Map<String, Integer>> yearMap = userNameMap.get(username);
        if(yearMap == null){
            yearMap = new HashMap<>();
        }
        Map<String, Integer> monthMap = yearMap.get(year);
        if(monthMap == null){
            monthMap = new HashMap<>();
        }
        Integer integer = monthMap.get(month);
        if(integer == null){
            integer = 0;
            monthMap.put(month,integer);
            yearMap.put(year,monthMap);
            userNameMap.put(username,yearMap);
            TYPE_MAP.put(type,userNameMap);
        }
        return integer;
    }

}
