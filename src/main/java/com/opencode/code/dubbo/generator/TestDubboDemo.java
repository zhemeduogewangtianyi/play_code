package com.opencode.code.dubbo.generator;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            List<Map<String, Object>> result = JsonParseUtil.jsonMapsToList(o);

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
