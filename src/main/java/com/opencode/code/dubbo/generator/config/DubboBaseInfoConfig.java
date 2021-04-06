package com.opencode.code.dubbo.generator.config;

import com.opencode.code.dubbo.generator.descriptor.ParameterDescriptor;
import com.opencode.code.dubbo.generator.descriptor.ReturnDataDescriptor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DubboBaseInfoConfig {

    private String name;
    private String createPerson;
    private String modifiedPerson;
    private Date gmtCreate;
    private Date gmtModified;

    private String interfaceName;
    private String methodName;
    private String version;
    private String group;

    private List<ParameterDescriptor> parameters;
    private List<ReturnDataDescriptor> returns;

    private String generic;

}
