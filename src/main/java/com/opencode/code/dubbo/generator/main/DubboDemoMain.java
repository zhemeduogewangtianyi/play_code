package com.opencode.code.dubbo.generator.main;

import com.opencode.code.dubbo.generator.TestDubboDemo;
import com.opencode.code.dubbo.generator.config.DubboBaseInfoConfig;
import com.opencode.code.dubbo.generator.descriptor.ParameterDescriptor;
import com.opencode.code.dubbo.generator.descriptor.ReturnDataDescriptor;

import java.util.*;

public class DubboDemoMain {

    public static void main(String[] args) throws Exception {

        Object o = new TestDubboDemo().testDubboDemo();
        System.out.println(o);
    }

    public static DubboBaseInfoConfig selectById(){
        DubboBaseInfoConfig infoConfig = new DubboBaseInfoConfig();

        infoConfig.setInterfaceName("com.opencode.code.dubbo.provider.service.GreetingService");
        infoConfig.setGroup("DUBBO");
        infoConfig.setVersion("1.0.0.daily");
        infoConfig.setMethodName("sayTest");
        infoConfig.setGeneric("true");

        List<ParameterDescriptor> parameters = new ArrayList<>();
        ParameterDescriptor name = new ParameterDescriptor("java.lang.String");
        name.addName("name");
        ParameterDescriptor age = new ParameterDescriptor("java.lang.Integer");
        age.addName("age");
        ParameterDescriptor slsAddParam = new ParameterDescriptor("com.opencode.code.dubbo.generator.pojo.TestDubboParam");
        slsAddParam.addName("name");
        slsAddParam.addName("age");
        slsAddParam.addName("createTime");
        slsAddParam.addName("map");
        Collections.addAll(parameters,name,age,slsAddParam);
        infoConfig.setParameters(parameters);

        List<ReturnDataDescriptor> returns = new ArrayList<>();

        ReturnDataDescriptor nameDescriptor = new ReturnDataDescriptor();
        nameDescriptor.setType("java.lang.String");
        nameDescriptor.setName("name");
        returns.add(nameDescriptor);


        ReturnDataDescriptor dataOne = new ReturnDataDescriptor();
        dataOne.setType("java.lang.Map");
        dataOne.setName("map.d1");
        returns.add(dataOne);

        ReturnDataDescriptor dataTwo = new ReturnDataDescriptor();
        dataTwo.setType("java.lang.Map");
        dataTwo.setName("map.d2");
        returns.add(dataTwo);

        infoConfig.setReturns(returns);

        return infoConfig;
    }

    public static DubboBaseInfoConfig selectById1(){
        DubboBaseInfoConfig infoConfig = new DubboBaseInfoConfig();

        infoConfig.setInterfaceName("com.opencode.code.dubbo.provider.service.GreetingService");
        infoConfig.setGroup("DUBBO");
        infoConfig.setVersion("1.0.0.daily");
        infoConfig.setMethodName("sayTestArr");
        infoConfig.setGeneric("true");

        List<ParameterDescriptor> parameters = new ArrayList<>();
        ParameterDescriptor name = new ParameterDescriptor("java.lang.String");
        name.addName("name");
        ParameterDescriptor age = new ParameterDescriptor("java.lang.Integer");
        age.addName("age");
        ParameterDescriptor slsAddParam = new ParameterDescriptor("com.opencode.code.dubbo.generator.pojo.TestDubboParam");
        slsAddParam.addName("name");
        slsAddParam.addName("age");
        slsAddParam.addName("createTime");
        slsAddParam.addName("map");
        Collections.addAll(parameters,name,age,slsAddParam);
        infoConfig.setParameters(parameters);

        List<ReturnDataDescriptor> returns = new ArrayList<>();

        ReturnDataDescriptor nameDescriptor = new ReturnDataDescriptor();
        nameDescriptor.setType("java.lang.String");
        nameDescriptor.setName("data.name");
        returns.add(nameDescriptor);


        ReturnDataDescriptor dataOne = new ReturnDataDescriptor();
        dataOne.setType("java.lang.Map");
        dataOne.setName("data.map.d1");
        returns.add(dataOne);

        ReturnDataDescriptor dataTwo = new ReturnDataDescriptor();
        dataTwo.setType("java.lang.Map");
        dataTwo.setName("data.map.d2");
        returns.add(dataTwo);

        infoConfig.setReturns(returns);

        return infoConfig;
    }

    public static List<Map<String,Object>> getDataList(){
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> listMap = new HashMap<>();

        Map<String,Object> map = new HashMap<>();
        map.put("d1","d1");
        map.put("d2",2);
        listMap.put("map",map);

        listMap.put("createTime",new Date());

        listMap.put("age","18");
        listMap.put("name","DDDXHH");

        list.add(listMap);
        return list;
    }

}
