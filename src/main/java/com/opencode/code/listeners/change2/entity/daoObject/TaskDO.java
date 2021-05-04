package com.opencode.code.listeners.change2.entity.daoObject;

import lombok.Data;

@Data
public class TaskDO {

    private Long id;

    private Integer type;

    private Integer status;

    private Long dataSourceId;

    private Long etlId;

    private Long templateId;

    private Long deliveryId;

    private Integer step;

    private Integer reportCnt;

}
