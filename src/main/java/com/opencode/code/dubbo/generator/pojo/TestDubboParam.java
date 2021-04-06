package com.opencode.code.dubbo.generator.pojo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class TestDubboParam {

    private String name;

    private Integer age;

    private Date createTime;

    private Map<String,Object> map;

}
