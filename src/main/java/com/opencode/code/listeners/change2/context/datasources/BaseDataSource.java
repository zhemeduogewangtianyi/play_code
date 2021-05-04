package com.opencode.code.listeners.change2.context.datasources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDataSource {

    private Long dataSourceId;

    protected Integer type;

    protected Integer status;

    protected ODPSDataSource odpsDataSource;

    protected MetaqDataSource metaqDataSource;

    protected HttpDataSource httpDataSource;

    protected TTDataSource ttDataSource;

}
