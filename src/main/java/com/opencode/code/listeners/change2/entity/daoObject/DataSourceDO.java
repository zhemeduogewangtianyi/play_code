package com.opencode.code.listeners.change2.entity.daoObject;

import lombok.Data;

@Data
public class DataSourceDO {

    private Long id;

    private String name;

    private Integer type;

    private Integer status;

    private String dataSourceConfig;

}
