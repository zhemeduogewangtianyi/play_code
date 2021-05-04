package com.opencode.code.listeners.change2.context.etl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtlContext {

    private Long etlId;

    private Integer status;

    private String etlMapping;

    private String filterMapping;

}
