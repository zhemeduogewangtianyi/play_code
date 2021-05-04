package com.opencode.code.listeners.change2.run.report.builde;

import com.alibaba.fastjson.JSONObject;
import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.context.datasources.*;
import com.opencode.code.listeners.change2.context.deliverys.*;
import com.opencode.code.listeners.change2.context.etl.EtlContext;
import com.opencode.code.listeners.change2.context.template.TemplateContext;
import com.opencode.code.listeners.change2.entity.daoObject.*;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.enums.DataProcessorStatusEnum;
import com.opencode.code.listeners.change2.enums.DataSourceTypeEnum;
import com.opencode.code.listeners.change2.enums.DeliveryTypeEnum;
import com.opencode.code.listeners.change2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuilderReportContext {

    @Autowired
    private TaskService taskService;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private EtlService etlService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DeliveryService deliveryService;

    public ReportContext builderReportContext(ReportContext context){

        context.setStatus(DataProcessorStatusEnum.PROCESSOR.getType());

        context.setRequestId(UUID.randomUUID().toString());
        context.setCreateTime(System.currentTimeMillis());
        context.setNode(DataProcessorNodeEnum.PRODUCER.getType());
        context.setStatus(DataProcessorStatusEnum.PROCESSOR.getType());

        TaskDO taskDO = taskService.queryById(context.getTaskId());

        context.setStep(taskDO.getStep());
        context.setReportCnt(taskDO.getReportCnt());

        Long dataSourceId = taskDO.getDataSourceId();

        DataSourceDO dataSourceDO = dataSourceService.queryById(dataSourceId);
        Integer type = dataSourceDO.getType();
        String dataSourceConfig = dataSourceDO.getDataSourceConfig();

        BaseDataSource baseDataSource = new BaseDataSource();
        baseDataSource.setDataSourceId(dataSourceId);
        baseDataSource.setStatus(dataSourceDO.getStatus());
        baseDataSource.setType(type);

        DataSourceTypeEnum dataSourceTypeEnum = DataSourceTypeEnum.getByType(type);
        if(dataSourceTypeEnum != null){
            switch (dataSourceTypeEnum){
                case ODPS:
                    ODPSDataSource odpsDataSource = JSONObject.parseObject(dataSourceConfig, ODPSDataSource.class);
                    baseDataSource.setOdpsDataSource(odpsDataSource);
                    break;
                case METAQ:
                    MetaqDataSource metaqDataSource = JSONObject.parseObject(dataSourceConfig, MetaqDataSource.class);
                    baseDataSource.setMetaqDataSource(metaqDataSource);
                    break;
                case HTTP:
                case HTTPS:
                    HttpDataSource httpDataSource = JSONObject.parseObject(dataSourceConfig, HttpDataSource.class);
                    baseDataSource.setHttpDataSource(httpDataSource);
                    break;
                case TT:
                    TTDataSource ttDataSource = JSONObject.parseObject(dataSourceConfig, TTDataSource.class);
                    baseDataSource.setTtDataSource(ttDataSource);
                    break;
            }
        }

        context.setDataSource(baseDataSource);


        Long etlId = taskDO.getEtlId();
        EtlDO etlDO = etlService.queryById(etlId);

        EtlContext etlContext = new EtlContext();
        etlContext.setEtlId(etlId);
        etlContext.setEtlMapping(etlDO.getEtlMapping());
        etlContext.setFilterMapping(etlDO.getFilterMapping());
        etlContext.setStatus(etlDO.getStatus());

        context.setEtlContext(etlContext);


        Long templateId = taskDO.getTemplateId();
        TemplateDO templateDO = templateService.queryById(templateId);

        TemplateContext templateContext = new TemplateContext();
        templateContext.setFileName(templateDO.getFileName());
        templateContext.setNeedZip(templateDO.getNeedZip());
        templateContext.setZipPassword(templateDO.getZipPassword());
        templateContext.setStatus(templateDO.getStatus());
        templateContext.setTemplateId(templateId);
        templateContext.setTemplate(templateDO.getTemplate());

        context.setTemplateContext(templateContext);

        Long deliveryId = taskDO.getDeliveryId();
        DeliveryDO deliveryDO = deliveryService.queryById(deliveryId);

        BaseDelivery baseDelivery = new BaseDelivery();
        Integer deliveryType = deliveryDO.getType();
        baseDelivery.setType(deliveryType);
        baseDelivery.setDeliveryId(deliveryId);
        baseDelivery.setStatus(deliveryDO.getStatus());
        baseDelivery.setName(deliveryDO.getName());

        String deliveryConfig = deliveryDO.getDeliveryConfig();

        DeliveryTypeEnum deliveryTypeEnum = DeliveryTypeEnum.getByType(deliveryType);
        switch (deliveryTypeEnum){
            case FTP:
                FTPDelivery ftpDelivery = JSONObject.parseObject(deliveryConfig, FTPDelivery.class);
                baseDelivery.setFtpDelivery(ftpDelivery);
                break;
            case SFTP:
                SFTPDelivery sftpDelivery = JSONObject.parseObject(deliveryConfig, SFTPDelivery.class);
                baseDelivery.setSftpDelivery(sftpDelivery);
                break;
            case HTTP:
            case HTTPS:
                HTTPDelivery httpDelivery = JSONObject.parseObject(deliveryConfig, HTTPDelivery.class);
                baseDelivery.setHttpDelivery(httpDelivery);
                break;
            case ODPS:
                ODPSDelivery odpsDelivery = JSONObject.parseObject(deliveryConfig, ODPSDelivery.class);
                baseDelivery.setOdpsDelivery(odpsDelivery);
                break;
            case OSS:
                OSSDelivery ossDelivery = JSONObject.parseObject(deliveryConfig, OSSDelivery.class);
                baseDelivery.setOssDelivery(ossDelivery);
                break;
            case SLS:
                SLSDelivery slsDelivery = JSONObject.parseObject(deliveryConfig, SLSDelivery.class);
                baseDelivery.setSlsDelivery(slsDelivery);
                break;

        }

        context.setDelivery(baseDelivery);

        context.setDir("d://change-report-cache");

        context.setFileName(templateDO.getFileName());

        return context;
    }


}
