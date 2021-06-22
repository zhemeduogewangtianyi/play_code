package com.opencode.carrot.csql.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id ;

    private String name;

    private String desc;

    private Integer age;

    private String url;

    private Date birthDay;

}
