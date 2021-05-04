package com.opencode.code.listeners.change2.context;

import com.opencode.code.listeners.change2.context.datasources.BaseDataSource;
import com.opencode.code.listeners.change2.context.deliverys.BaseDelivery;
import com.opencode.code.listeners.change2.context.etl.EtlContext;
import com.opencode.code.listeners.change2.context.template.TemplateContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportContext {

    protected String requestId;

    private Long createTime;

    private Integer node;

    private Integer status;

    private boolean runTimeCheckChange;

    private String errorMsg;



    private String dir;

    private String fileName;



    private Long taskId;

    private Integer step;

    private Integer reportCnt;



    private BaseDataSource dataSource;

    private EtlContext etlContext;

    private TemplateContext templateContext;

    private BaseDelivery delivery;
    
}
