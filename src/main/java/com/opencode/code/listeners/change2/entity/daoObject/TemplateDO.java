package com.opencode.code.listeners.change2.entity.daoObject;

import lombok.Data;

@Data
public class TemplateDO {

    private Long id;

    private String name;

    private Integer status;

    private String template;

    private String fileName;

    private Integer needZip;

    private String zipPassword;

}
