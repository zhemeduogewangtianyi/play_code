package com.opencode.code.dubbo.generator.descriptor;


import com.opencode.code.dubbo.generator.enums.ParameterTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterDescriptor {

    /**
     * 是否要弄成一个 map 作为入参
     * */
    private boolean isMap = false;

    /**
     * 参数类型
     */
    private final String type;

    /**
     * 非四类八种的 type 描述
     * */
    private final String sourceType;

    /**
     * 参数名称
     */
    private final List<String> names = new ArrayList<>();

    public ParameterDescriptor(String type) {
        ParameterTypeEnum byName = ParameterTypeEnum.getByName(type);
        if (byName == null || byName.equals(ParameterTypeEnum.MAP)) {
            isMap = true;
            this.type = ParameterTypeEnum.MAP.getName();
            this.sourceType = type;
        }else{
            this.type = type;
            this.sourceType = type;
        }

    }

    public boolean addName(String name) {
        if (this.type.equals(Map.class.getName())) {
            this.names.add(name);
            return true;
        }
        if (this.names.size() < 1) {
            this.names.add(name);
            return true;
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public List<String> getNames() {
        return names;
    }

    public boolean isMap() {
        return isMap;
    }

    public String getSourceType() {
        return sourceType;
    }
}
