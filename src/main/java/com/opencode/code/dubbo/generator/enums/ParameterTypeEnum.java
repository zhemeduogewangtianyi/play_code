package com.opencode.code.dubbo.generator.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ParameterTypeEnum {

    STRING(String.class.getName(),String.class,1),
    STRING_ARR(String[].class.getName(),String[].class,2),

    BYTE(Byte.class.getName(),Byte.class,1),
    BYTE_ARR(Byte[].class.getName(),Byte[].class,2),

    SHORT(Short.class.getName(),Short.class,1),
    SHORT_ARR(Short[].class.getName(),Short[].class,2),

    INTEGER(Integer.class.getName(),Integer.class,1),
    INTEGER_ARR(Integer[].class.getName(),Integer[].class,2),

    LONG(Long.class.getName(),Long.class,1),
    LONG_ARR(Long[].class.getName(),Long[].class,2),

    DOUBLE(Double.class.getName(),Double.class,1),
    DOUBLE_ARR(Double[].class.getName(),Double[].class,2),

    FLOAT(Float.class.getName(),Float.class,1),
    FLOAT_ARR(Float[].class.getName(),Float[].class,2),

    BOOLEAN(Boolean.class.getName(),Boolean.class,1),
    BOOLEAN_ARR(Boolean[].class.getName(),Boolean[].class,2),

    CHAR(Character.class.getName(),Character.class,1),
    CHAR_ARR(Character[].class.getName(),Character[].class,2),

    MAP(Map.class.getName(),Map.class,3),
    ;

    private String name;
    private Class cls;
    private Integer type;

    ParameterTypeEnum(String name, Class cls, Integer type) {
        this.name = name;
        this.cls = cls;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class getCls() {
        return cls;
    }

    public Integer getType() {
        return type;
    }

    private static final Map<String, ParameterTypeEnum> cacheMap = new HashMap<>();
    private static final Map<String, ParameterTypeEnum> baseTypeCacheMap = new HashMap<>();

    static{
        for(ParameterTypeEnum typeEmum : ParameterTypeEnum.values()){
            if(typeEmum.getType().compareTo(3) != 0){
                cacheMap.put(typeEmum.getName(),typeEmum);
            }
            if(typeEmum.getType().compareTo(1) == 0){
                baseTypeCacheMap.put(typeEmum.getName() , typeEmum);
            }
        }

    }

    public static ParameterTypeEnum getByName(String name){
        ParameterTypeEnum parameterTypeEnum = cacheMap.get(name);
        return parameterTypeEnum == null ? ParameterTypeEnum.MAP : parameterTypeEnum;
    }

    public static List<ParameterTypeEnum> getByNames(String[] name){
        List<ParameterTypeEnum> res = new ArrayList<>();
        for (String n : name){
            ParameterTypeEnum parameterTypeEnum = cacheMap.get(n);
            if(parameterTypeEnum != null){
                res.add(parameterTypeEnum);
            }
        }
        return res;
    }

    public static ParameterTypeEnum getBaseTypeByName(String name){
        return baseTypeCacheMap.get(name);
    }

}
