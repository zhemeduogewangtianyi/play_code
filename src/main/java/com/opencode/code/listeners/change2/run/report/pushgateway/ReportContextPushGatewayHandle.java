package com.opencode.code.listeners.change2.run.report.pushgateway;

import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.context.datasources.BaseDataSource;
import com.opencode.code.listeners.change2.context.deliverys.*;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.enums.DataSourceTypeEnum;
import com.opencode.code.listeners.change2.enums.DeliveryTypeEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportContextPushGatewayHandle implements Handle<ReportContext> {


    @Override
    public Object handle(ReportContext context) throws Exception {
        File file = new File(context.getDir() + File.separator + context.getFileName());
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        int len;
        byte[] data = new byte[4096];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((len = bis.read(data)) != -1){
            baos.write(data , 0 , len);
        }
        baos.close();
        bis.close();
        fis.close();
        return new String(baos.toByteArray(),0 , baos.size());
    }

    @Override
    public int getOrder() {
        return 1;
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
        if(reportContext.getDelivery() == null){
            return false;
        }
        return true;
    }
}
