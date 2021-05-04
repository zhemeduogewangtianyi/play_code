package com.opencode.code.listeners.change2.run.report.producer;

import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.enums.DataProcessorStatusEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import com.opencode.code.listeners.change2.interfaces.Interceptor;
import com.opencode.code.listeners.change2.mock.Redis;
import org.springframework.stereotype.Component;

@Component
public class ReportContextProducerInterceptor implements Interceptor<ReportContext> {

    @Override
    public Object pre(Handle<ReportContext> handle, ReportContext context) {
        if(handle == null){
            return "not found handle !";
        }
        if(context == null){
            return "param can't null !";
        }
        if(context.getTaskId() == null){
            return "taskId can't null !";
        }
        if(context.getNode() == null){
            return "node can't null !";
        }
        return null;
    }

    @Override
    public void post(Handle<ReportContext> handle, ReportContext context, Object result) throws Throwable {

    }

    @Override
    public Object after(Handle<ReportContext> handle, ReportContext context, Throwable e) throws Throwable {
        if(e == null){
            //TODO 落库、更新
            Redis.push(DataProcessorNodeEnum.PRODUCER.getSender() + context.getTaskId() , context);
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
        if(!DataProcessorNodeEnum.PRODUCER.getType().equals(reportContext.getNode())){
            return false;
        }
        if(reportContext.getTaskId() == null){
            return false;
        }
        return true;
    }
}
