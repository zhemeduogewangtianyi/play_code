package com.opencode.code.listeners.change2.run.report.pushgateway;

import com.alibaba.fastjson.JSONArray;
import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.context.deliverys.*;
import com.opencode.code.listeners.change2.context.etl.EtlContext;
import com.opencode.code.listeners.change2.entity.param.EtlFilterParam;
import com.opencode.code.listeners.change2.entity.param.EtlMappingParam;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.enums.DataProcessorStatusEnum;
import com.opencode.code.listeners.change2.enums.DeliveryTypeEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import com.opencode.code.listeners.change2.interfaces.Interceptor;
import com.opencode.code.listeners.change2.mock.Redis;
import com.opencode.code.velocity.VelocityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportContextPushGatewayInterceptor implements Interceptor<ReportContext> {

    @Override
    public Object pre(Handle<ReportContext> handle, ReportContext context) {
        if(context == null){
            return "param can't null !";
        }
        if(context.getTaskId() == null){
            return "taskId can't null !";
        }
        if(context.getNode() == null){
            return "node can't null !";
        }
        if(context.getDelivery() == null){
            return "datasource config can't null !";
        }
        if(StringUtils.isEmpty(context.getFileName())){
            return "cache fileName can't null !";
        }
        return null;
    }

    @Override
    public void post(Handle<ReportContext> handle, ReportContext context, Object result) throws Throwable {
        if(result == null){
            return;
        }
        BaseDelivery delivery = context.getDelivery();
        Integer type = delivery.getType();
        DeliveryTypeEnum deliveryTypeEnum = DeliveryTypeEnum.getByType(type);
        if(deliveryTypeEnum == null){
            throw new RuntimeException("data source type error !");
        }

        boolean success = false;

        switch (deliveryTypeEnum){
            case ODPS:
                //TODO ODPS
                ODPSDelivery odpsDelivery = delivery.getOdpsDelivery();
                success = true;
                break;
            case FTP:
                //TODO FTP
                FTPDelivery ftpDelivery = delivery.getFtpDelivery();
                success = true;
                break;
            case SFTP:
                //TODO SFTP
                SFTPDelivery sftpDelivery = delivery.getSftpDelivery();
                success = true;
                break;
            case HTTP:
                //TODO HTTP
            case HTTPS:
                //TODO HTTPS
                HTTPDelivery httpDelivery = delivery.getHttpDelivery();
                success = true;
                break;
            case SLS:
                //TODO SLS
                SLSDelivery slsDelivery = delivery.getSlsDelivery();
                success = true;
                break;
            case OSS:
                //TODO OSS
                OSSDelivery ossDelivery = delivery.getOssDelivery();
                success = true;
                break;
        }

        System.out.println(result + " " + success);

    }

    @Override
    public Object after(Handle<ReportContext> handle, ReportContext context, Throwable e) throws Throwable {
        if(e == null){

            //TODO 落库、更新
            DataProcessorNodeEnum end = DataProcessorNodeEnum.END;

            return context;
        }else{
            context.setStatus(DataProcessorStatusEnum.FAIL.getType());
            context.setErrorMsg(e.getMessage());
            return context;
        }

    }

    @Override
    public void hook(Handle<ReportContext> handle, ReportContext context) {

    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean available(ReportContext e) {
        return true;
    }

    @Override
    public boolean support(ReportContext reportContext) {
        if(reportContext == null){
            return false;
        }
        if(!DataProcessorNodeEnum.PUSH.getType().equals(reportContext.getNode())){
            return false;
        }
        if(reportContext.getTaskId() == null){
            return false;
        }
        return true;
    }
}
