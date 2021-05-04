package com.opencode.code.listeners.change2.service.impl;

import com.alibaba.fastjson.JSON;
import com.opencode.code.listeners.change2.context.datasources.ODPSDataSource;
import com.opencode.code.listeners.change2.entity.daoObject.DataSourceDO;
import com.opencode.code.listeners.change2.enums.DataSourceTypeEnum;
import com.opencode.code.listeners.change2.enums.TaskTypeEnum;
import com.opencode.code.listeners.change2.service.DataSourceService;
import org.springframework.stereotype.Component;

@Component
public class DataSourceServiceImpl implements DataSourceService {

    @Override
    public DataSourceDO queryById(Long id) {

        DataSourceDO dataSourceDO = new DataSourceDO();
        dataSourceDO.setId(id);
        dataSourceDO.setType(DataSourceTypeEnum.ODPS.getType());
        dataSourceDO.setStatus(1);
        dataSourceDO.setName("数据源");

        ODPSDataSource odpsDataSource = new ODPSDataSource();
        odpsDataSource.setAccessKeyId("123456");
        odpsDataSource.setAccessKeySecret("abcdef");
        odpsDataSource.setEndPoint("http://www.baidu.com");
        odpsDataSource.setProject("project");
        odpsDataSource.setTableName("tableName");

        dataSourceDO.setDataSourceConfig(JSON.toJSONString(odpsDataSource));
        
        return dataSourceDO;
    }

}
