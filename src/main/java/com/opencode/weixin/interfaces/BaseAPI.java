package com.opencode.weixin.interfaces;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface BaseAPI extends ApiURL{

    Map<String, List<Cookie>> COOKIE = new ConcurrentHashMap<>();

    Map<String,String> GET_HEADER = new HashMap<String,String>(){
        {

            put("User-Agent",USER_AGENT);
            put("Content-Type","application/x-www-form-urlencoded");

        }
    };

}
