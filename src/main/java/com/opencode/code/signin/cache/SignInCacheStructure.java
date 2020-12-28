package com.opencode.code.signin.cache;

import com.opencode.code.signin.bean.SignInContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 签到缓存结构 */
@Component
public class SignInCacheStructure {

    //type -> name -> year -> month -> count
    private static final Map<String,Map<String,Map<String, Map<String,SignInContext>>>> TYPE_MAP = new HashMap<>();

    public boolean cacheCount(SignInContext signInContext){

        String type = signInContext.getType();
        String username = signInContext.getUsername();
        String year = signInContext.getYear();
        String month = signInContext.getMonth();

        Map<String, Map<String, Map<String, SignInContext>>> userNameMap = TYPE_MAP.get(type);
        if(userNameMap == null){
            userNameMap = new HashMap<>();
        }
        Map<String, Map<String, SignInContext>> yearMap = userNameMap.get(username);
        if(yearMap == null){
            yearMap = new HashMap<>();
        }
        Map<String, SignInContext> monthMap = yearMap.get(year);
        if(monthMap == null){
            monthMap = new HashMap<>();
        }

        SignInContext sc = monthMap.get(month);
        Date firstSignInTime = sc.getFirstSignInTime();
        if(firstSignInTime == null){
            firstSignInTime = new Date();
        }
        Integer countDay = sc.getCountDay();
        if(countDay == null){
            countDay = 0;
        }

        Integer count = signInContext.getBitSet();
        if(count == null){
            count = 0;
        }
        signInContext.setBitSet(count);
        signInContext.setCountDay(countDay + 1);
        signInContext.setFirstSignInTime(firstSignInTime);
        signInContext.setLastSignInTime(new Date());

        monthMap.put(month,signInContext);
        yearMap.put(year,monthMap);
        userNameMap.put(username,yearMap);
        TYPE_MAP.put(type,userNameMap);
        return true;
    }

    public Integer getCacheCount(SignInContext signInContext){

        String type = signInContext.getType();
        String username = signInContext.getUsername();
        String year = signInContext.getYear();
        String month = signInContext.getMonth();

        Map<String, Map<String, Map<String, SignInContext>>> userNameMap = TYPE_MAP.get(type);
        if(userNameMap == null){
            userNameMap = new HashMap<>();
        }
        Map<String, Map<String, SignInContext>> yearMap = userNameMap.get(username);
        if(yearMap == null){
            yearMap = new HashMap<>();
        }
        Map<String, SignInContext> monthMap = yearMap.get(year);
        if(monthMap == null){
            monthMap = new HashMap<>();
        }
        SignInContext integer = monthMap.get(month);
        if(integer == null){
            integer = signInContext;
            integer.setBitSet(0);
            monthMap.put(month,integer);
            yearMap.put(year,monthMap);
            userNameMap.put(username,yearMap);
            TYPE_MAP.put(type,userNameMap);
        }
        return integer.getBitSet();
    }

    public static Map<String,Map<String,Map<String, Map<String,SignInContext>>>> getTypeMapInfo(){
        return TYPE_MAP;
    }

}
