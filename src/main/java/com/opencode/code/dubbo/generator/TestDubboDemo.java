package com.opencode.code.dubbo.generator;

import com.alibaba.fastjson.JSON;
import com.opencode.code.dubbo.generator.config.DubboBaseInfoConfig;
import com.opencode.code.dubbo.generator.descriptor.ParameterDescriptor;
import com.opencode.code.dubbo.generator.descriptor.ReturnDataDescriptor;
import com.opencode.code.dubbo.generator.enums.ParameterTypeEnum;
import com.opencode.code.dubbo.generator.main.DubboDemoMain;
import com.opencode.code.dubbo.generator.util.JsonParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.*;
import java.util.stream.Collectors;

public class TestDubboDemo {

    public Object testDubboDemo() throws Exception {

//        DubboBaseInfoConfig config = DubboDemoMain.selectById();
        DubboBaseInfoConfig config = DubboDemoMain.selectById1();

        List<Map<String, Object>> dataList = DubboDemoMain.getDataList();

        List<String> parameterTypeList = new ArrayList<>();
        List<ParameterDescriptor> parameters = config.getParameters();
        List<Object> valueList = new ArrayList<>();

        for(ParameterDescriptor ps : parameters){
            String type = ps.getType();
            parameterTypeList.add(StringUtils.isNotBlank(ps.getSourceType()) ? ps.getSourceType() : ParameterTypeEnum.getByName(type).getName());
            List<String> names = ps.getNames();

            if(ps.isMap()){
                Map<String,Object> paramMap = new HashMap<>();
                for(String name : names){
                    for(Map<String, Object> data : dataList){
                        paramMap.put(name,data.get(name));
                    }
                }
                paramMap.put("class",ps.getSourceType());
                valueList.add(paramMap);
            }else{
                for(String name : names){
                    for(Map<String, Object> data : dataList){
                        valueList.add(data.get(name));
                    }
                }
            }
        }

        String[] parameterType = parameterTypeList.toArray(new String[]{});
        Object[] parameter = valueList.toArray();

        ReferenceConfig consumerBean = createBean(config);

        GenericService hWService = (GenericService) consumerBean.get();
        try{
            Object o = hWService.$invoke(config.getMethodName(), parameterType, parameter);

            Map<String,Object> jsonMap = new HashMap<>();
            int start = 0;
            JsonParseUtil.parseJSON2Map(start,jsonMap,JsonParseUtil.checkFormat(o instanceof String ? o.toString() : JSON.toJSONString(o)),null);

            Map<String, Map<String, List<Map.Entry<String, Object>>>> collect = jsonMap.entrySet().stream().collect(
                    Collectors.groupingBy(
                            c -> c.getKey().indexOf("$_") == -1 ? "one" : c.getKey().substring(c.getKey().indexOf("$_")
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

                if(key.equals("one")){
                   continue;
                }else{

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

            List<ReturnDataDescriptor> returns = config.getReturns();

            List<Map<String, Object>> resultList = new ArrayList<>();

            for(Map<String,Object> m : result){
                Map<String,Object> resMap = new HashMap<>();
                for(ReturnDataDescriptor rdd : returns){
                    Object o1 = m.get(rdd.getName());
                    if(o1 != null){
                        resMap.put(rdd.getName(),o1);
                    }
                }
                resultList.add(resMap);
            }

            return resultList;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ReferenceConfig<GenericService> createBean(DubboBaseInfoConfig config) throws Exception {
        ReferenceConfig<GenericService> consumerBean = new ReferenceConfig<>();
        consumerBean.setInterface(config.getInterfaceName());
        consumerBean.setGroup(config.getGroup());
        consumerBean.setGeneric(config.getGeneric());
        consumerBean.setVersion(config.getVersion());
//        consumerBean.setMaxWaitTimeForCsAddress(30000);
        consumerBean.setTimeout(30000);
        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-provider");
        consumerBean.setApplication(applicationConfig);
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        consumerBean.setRegistry(registryConfig);

        consumerBean.init();
        return consumerBean;
    }

//    public HSFApiConsumerBean createBean(HsfBaseInfoConfig config) throws Exception {
//        HSFApiConsumerBean consumerBean = new HSFApiConsumerBean();
//        consumerBean.setInterfaceName(config.getInterfaceName());
//        consumerBean.setGroup(config.getGroup());
//        consumerBean.setGeneric(config.getGeneric());
//        consumerBean.setVersion(config.getVersion());
//        consumerBean.setMaxWaitTimeForCsAddress(30000);
//        MethodSpecial ms = new MethodSpecial();
//        ms.setClientTimeout(30000);
//        ms.setMethodName(config.getMethodName());
//        MethodSpecial[] p = {ms};
//        consumerBean.setMethodSpecials(p);
//        consumerBean.init();
//        return consumerBean;
//    }

}
