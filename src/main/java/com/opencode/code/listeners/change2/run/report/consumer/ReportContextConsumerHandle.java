package com.opencode.code.listeners.change2.run.report.consumer;

import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.context.datasources.BaseDataSource;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.enums.DataSourceTypeEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportContextConsumerHandle implements Handle<ReportContext> {


    @Override
    public Object handle(ReportContext context) {
        BaseDataSource dataSource = context.getDataSource();
        Integer type = dataSource.getType();
        DataSourceTypeEnum dataSourceTypeEnum = DataSourceTypeEnum.getByType(type);
        if(dataSourceTypeEnum == null){
            throw new RuntimeException("data source type error !");
        }
        List<Map<String,Object>> datas = new ArrayList<>();
        switch (dataSourceTypeEnum){
            case ODPS:
                //TODO ODPS
                break;
            case METAQ:
                //TODO metaq
                break;
            case HTTP:
                //TODO http
            case HTTPS:
                //TODO https
                break;
            case TT:
                //TODO tt
                break;
        }
        Map<String,Object> data = new HashMap<>();
        data.put("a","123");
        data.put("b","123");
        data.put("c","123");
        datas.add(data);
        return datas;
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
        if(!DataProcessorNodeEnum.CONSUMER.getType().equals(reportContext.getNode())){
            return false;
        }
        if(reportContext.getTaskId() == null){
            return false;
        }
        return true;
    }
}
