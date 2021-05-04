package com.opencode.code.listeners.change2.service.impl;

import com.alibaba.fastjson.JSON;
import com.opencode.code.listeners.change2.context.datasources.ODPSDataSource;
import com.opencode.code.listeners.change2.entity.daoObject.DataSourceDO;
import com.opencode.code.listeners.change2.entity.daoObject.EtlDO;
import com.opencode.code.listeners.change2.entity.param.EtlFilterParam;
import com.opencode.code.listeners.change2.entity.param.EtlMappingParam;
import com.opencode.code.listeners.change2.enums.TaskTypeEnum;
import com.opencode.code.listeners.change2.service.DataSourceService;
import com.opencode.code.listeners.change2.service.EtlService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EtlServiceImpl implements EtlService {

    @Override
    public EtlDO queryById(Long id) {

        EtlDO etlDO = new EtlDO();
        etlDO.setId(1L);
        etlDO.setStatus(1);

        EtlMappingParam m1 = new EtlMappingParam();
        m1.setSource("a");
        m1.setTarget("A");

        EtlMappingParam m2 = new EtlMappingParam();
        m2.setSource("b");
        m2.setTarget("B");

        EtlMappingParam m3 = new EtlMappingParam();
        m3.setSource("c");
        m3.setTarget("C");

        List<EtlMappingParam> etlMappingParams = new ArrayList<>();
        Collections.addAll(etlMappingParams,m1,m2,m3);

        etlDO.setEtlMapping(JSON.toJSONString(etlMappingParams));

        EtlFilterParam f1 = new EtlFilterParam();
        f1.setFilter("a");

        List<EtlFilterParam> etlFilterParams = new ArrayList<>();
        etlFilterParams.add(f1);

        etlDO.setFilterMapping(JSON.toJSONString(etlFilterParams));

        return etlDO;
    }

}
