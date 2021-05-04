package com.opencode.code.listeners.change2.entity.daoObject;

import lombok.Data;

@Data
public class EtlDO {

    private Long id;

    private Integer status;

    private String etlMapping;

    private String filterMapping;

}
