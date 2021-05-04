package com.opencode.code.listeners.change2.run.report.producer;

import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import com.opencode.code.listeners.change2.run.report.builde.BuilderReportContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportContextProducerHandle implements Handle<ReportContext> {

    @Autowired
    private BuilderReportContext builderReportContext;

    @Override
    public Object handle(ReportContext context) {
        return builderReportContext.builderReportContext(context);
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
        if(!DataProcessorNodeEnum.PRODUCER.getType().equals(reportContext.getNode())){
            return false;
        }
        if(reportContext.getTaskId() == null){
            return false;
        }
        return true;
    }
}
