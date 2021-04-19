package com.opencode.code.dubbo.generator.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class JsonParseUtil {


    private static String checkFormat(String str) {
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
    private static Iterator<Map.Entry<String, Object>> printJsonMap(Map map) {
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
    private static void parseJSON2Map(int start,Map<String,Object> jsonMap,String jsonStr,String parentKey){
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

    /** JSON 解析成 List<Map> */
    public static List<Map<String,Object>> jsonMapsToList(Object o){

        Map<String,Object> jsonMap = new HashMap<>();
        int start = 0;
        JsonParseUtil.parseJSON2Map(start,jsonMap,JsonParseUtil.checkFormat(o instanceof String ? o.toString() : JSON.toJSONString(o)),null);

        Map<String, Map<String, List<Map.Entry<String, Object>>>> collect = jsonMap.entrySet().stream().collect(
                Collectors.groupingBy(
                        c -> !c.getKey().contains("$_") ? "one" : c.getKey().substring(c.getKey().indexOf("$_")
                        ),
                        Collectors.groupingBy(
                                k -> !k.getKey().contains(".") ? "common" : "$_1"
                        )
                )
        );

        List<Map<String,Object>> result = new ArrayList<>();

        Map<String, List<Map.Entry<String, Object>>> one = collect.get("one");
        List<Map.Entry<String, Object>> common = one.get("common");
        Map<String,Object> commonMap = new HashMap<>();
        for(Map.Entry<String,Object> comm : common){
            commonMap.put(comm.getKey(),comm.getValue());
        }

        List<Map.Entry<String, Object>> first = one.get("$_1");
        Map<String,Object> firstMap = new HashMap<>();
        for(Map.Entry<String,Object> f : first){
            firstMap.put(f.getKey(),f.getValue());
        }
        firstMap.putAll(commonMap);

        result.add(firstMap);

        for(Iterator<Map.Entry<String, Map<String, List<Map.Entry<String, Object>>>>> car = collect.entrySet().iterator(); car.hasNext() ; ){
            Map.Entry<String, Map<String, List<Map.Entry<String, Object>>>> next = car.next();
            String key = next.getKey();
            Map<String, List<Map.Entry<String, Object>>> value = next.getValue();

            if(!key.equals("one")){
                Map<String,Object> map = new HashMap<>();
                for(Iterator<Map.Entry<String, List<Map.Entry<String, Object>>>> two = value.entrySet().iterator(); two.hasNext() ; ){
                    Map.Entry<String, List<Map.Entry<String, Object>>> next1 = two.next();
                    List<Map.Entry<String, Object>> value1 = next1.getValue();
                    for(Map.Entry<String, Object> v1 : value1){
                        String key1 = v1.getKey();
                        map.put(key1.substring(0,key1.indexOf("$_")),v1.getValue());
                    }
                }
                map.putAll(commonMap);
                result.add(map);
            }
        }

        return result;
    }

    private static boolean isNested(Object jsonObj) {

        return jsonObj.toString().contains("{");
    }

}