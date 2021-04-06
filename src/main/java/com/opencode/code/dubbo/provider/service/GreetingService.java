package com.opencode.code.dubbo.provider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.dubbo.generator.pojo.TestDubboParam;

public interface GreetingService {

    String sayHi(String name);

    public Object sayTest(String name, Integer age, TestDubboParam param);

    public Object sayTestArr(String name, Integer age, TestDubboParam param);


}
