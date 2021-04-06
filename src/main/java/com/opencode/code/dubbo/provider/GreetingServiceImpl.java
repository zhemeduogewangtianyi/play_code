package com.opencode.code.dubbo.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.dubbo.generator.pojo.TestDubboParam;
import com.opencode.code.dubbo.provider.service.GreetingService;

import java.util.HashMap;
import java.util.Map;

public class GreetingServiceImpl implements GreetingService {

    @Override
    public String sayHi(String name) {
        return "say , hi " + name;
    }

    @Override
    public Object sayTest(String name, Integer age, TestDubboParam param) {
        String s = JSON.toJSONString(param);
        JSONObject jsonObject = JSONObject.parseObject(s);
        jsonObject.put("name",name);
        jsonObject.put("age",age);
        return jsonObject;
    }

    @Override
    public Object sayTestArr(String name, Integer age, TestDubboParam param) {
        String s = JSON.toJSONString(param);
        Map<String,Object> map = new HashMap<>();
        JSONArray array = new JSONArray();
        for(int i = 0 ; i < 1000 ; i ++){
            JSONObject jsonObject = JSONObject.parseObject(s);
            jsonObject.put("name",name + i);
            jsonObject.put("age",i);
            array.add(jsonObject);
        }
        map.put("code",200);
        map.put("success",true);
        map.put("data",array);
        return map;
    }

}
