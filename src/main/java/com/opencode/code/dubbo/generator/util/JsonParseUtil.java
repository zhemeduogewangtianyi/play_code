package com.opencode.code.dubbo.generator.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class JsonParseUtil {


    public static String checkFormat(String str) {
        String _str = str.trim();
        if (_str.startsWith("[") && _str.endsWith("]")) {
            return _str.substring(1, _str.length() - 1);
        }
        return _str;
    }

    /**
     * 打印Map中的数据
     *
     * @param map
     */
    public static Iterator<Map.Entry<String, Object>> printJsonMap(Map map) {
        Set entrySet = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = entrySet.iterator();
        //最外层提取
//        while(it.hasNext()){
//            Map.Entry<String, Object> e = it.next();
//            System.out.println("Key 值："+e.getKey()+"     Value 值："+e.getValue());
//        }
        return it;
    }


    /**
     * JSON 类型的字符串转换成 Map
     */
//    public static void parseJSON2Map(List<Map<String, Object>> jsonList, Map<String, Object> jsonMap, String jsonStr, String parentKey) {
//
//        Map<String, Object> temp = new HashMap<>();
//        //字符串转换成JSON对象
//        JSONObject json = JSONObject.parseObject(jsonStr);
//        //最外层JSON解析
//        for (Object k : json.keySet()) {
//            //JSONObject 实际上相当于一个Map集合，所以我们可以通过Key值获取Value
//            Object v = json.get(k);
//            //构造一个包含上层keyName的完整keyName
//            String fullKey = (null == parentKey || parentKey.trim().equals("") ? k.toString() : parentKey + "." + k);
//
//            if (v instanceof JSONArray) {
//                //如果内层还是数组的话，继续解析
//                Iterator it = ((JSONArray) v).iterator();
//                while (it.hasNext()) {
//                    JSONObject json2 = (JSONObject) it.next();
//                    parseJSON2Map(jsonList, jsonMap, json2.toString(), fullKey);
//                }
//            } else if (isNested(v)) {
//                parseJSON2Map(jsonList, jsonMap, v.toString(), fullKey);
//                jsonList.add(temp);
//            } else {
//                jsonMap.put(fullKey, v);
//                temp.put(fullKey,v);
//            }
//        }
//    }

    public static void parseJSON2Map(int start,Map<String,Object> jsonMap,String jsonStr,String parentKey){
        //字符串转换成JSON对象
        JSONObject  json = JSONObject.parseObject(jsonStr);
        //最外层JSON解析
        for(Object k : json.keySet()){
            //JSONObject 实际上相当于一个Map集合，所以我们可以通过Key值获取Value
            Object v = json.get(k);
            //构造一个包含上层keyName的完整keyName
            String fullKey = (null == parentKey || parentKey.trim().equals("") ? k.toString() : parentKey + "." + k);

            if(v instanceof JSONArray){
                //如果内层还是数组的话，继续解析
                Iterator it = ((JSONArray) v).iterator();
                while(it.hasNext()){
                    start++;
                    JSONObject json2 = (JSONObject)it.next();
                    parseJSON2Map(start,jsonMap,json2.toString(),fullKey);
                }
            } else if(isNested(v)){
                parseJSON2Map(start,jsonMap,v.toString(),fullKey);
            }
            else{
                if(jsonMap.containsKey(fullKey)){
                    jsonMap.put(fullKey + "$_"+start,v);
                }else{
                    jsonMap.put(fullKey,v);
                }

            }
        }
    }

    public static boolean isNested(Object jsonObj) {

        return jsonObj.toString().contains("{");
    }

    public static void println(Object str) {
        System.out.println(str);
    }
}