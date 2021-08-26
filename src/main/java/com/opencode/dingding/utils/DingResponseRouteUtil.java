package com.opencode.dingding.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;

import java.util.*;

public class DingResponseRouteUtil {

    private static final Character[] CHARS = {'a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final ArrayList<Character> CHAR_ARR = new ArrayList<>(Arrays.asList(CHARS));

    public static final Map<String, Class<? extends WebSocketResponse>> CALL_BACK_MAP = new HashMap<>();

    public static final Class<? extends WebSocketResponse> checkMid(String res){
        JSONObject jsonObject = JSONObject.parseObject(res);
        if(jsonObject != null){
            JSONObject headers = (JSONObject)jsonObject.get("headers");
            Object midObj = headers.get("mid");
            if(midObj != null){
                return CALL_BACK_MAP.get(midObj.toString());
            }
        }
        return null;
    }

    public static final Class<? extends WebSocketResponse> delMid(String res){
        JSONObject jsonObject = JSONObject.parseObject(res);
        if(jsonObject != null){
            JSONObject headers = (JSONObject)jsonObject.get("headers");
            Object midObj = headers.get("mid");
            if(midObj != null){
                return CALL_BACK_MAP.remove(midObj.toString());
            }
        }
        return null;
    }

    public static final String mackMid(Class<? extends WebSocketResponse> cls) {
        Collections.shuffle(CHAR_ARR);
        List<List<Character>> partition = Lists.partition(CHAR_ARR, 8);
        List<Character> characters = partition.get(0);
        StringBuilder buff = new StringBuilder();
        characters.forEach(buff::append);
        buff.append(" ").append("0");
        String mid = buff.toString();
        CALL_BACK_MAP.put(mid,cls);
        return mid;
    }

}
