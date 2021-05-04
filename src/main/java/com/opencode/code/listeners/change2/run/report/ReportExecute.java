package com.opencode.code.listeners.change2.run.report;

import com.alibaba.fastjson.JSON;
import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import com.opencode.code.listeners.change2.interfaces.Interceptor;
import com.opencode.code.listeners.change2.mock.Redis;
import com.opencode.code.listeners.change2.tpl.base.BaseHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Component
public class ReportExecute extends BaseHandle<ReportContext> implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportExecute.class);

    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("data-processor-flow");
                    return t;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        LOGGER.error("retry put Runnable fail !" , e);
                    }
                }
            });

    @Autowired
    private List<Interceptor<ReportContext>> reportInterceptor;

    @Autowired
    private List<Handle<ReportContext>> handles;

    @Override
    public void execute(ReportContext reportContext) {

        if(reportContext.getNode().equals(DataProcessorNodeEnum.PRODUCER.getType())){
            executeRun(reportContext);
        }else{
            pool.execute(() -> {
                DataProcessorNodeEnum dataProcessorNodeEnum = DataProcessorNodeEnum.getByType(reportContext.getNode());
                if(dataProcessorNodeEnum == null){
                    return;
                }
                Object poll = Redis.poll(dataProcessorNodeEnum.getReceiver() + reportContext.getTaskId());
                if(poll == null){
                    return ;
                }
                ReportContext context = (ReportContext) poll;
                context.setNode(reportContext.getNode());
                executeRun(context);
            });
        }



    }

    private void executeRun(ReportContext reportContext){

        Handle<ReportContext> handle = super.findHandle(reportContext);

        if(handle == null){
            return ;
        }

        Throwable throwable = null;

        try{
            Object o = super.applyPre(handle, reportContext);
            if(o != null){
                throw new Throwable(o.toString());
            }
            Object result = handle.handle(reportContext);
            super.applyPost(handle , reportContext , result );

        }catch(Throwable e){
            throwable = e;
            LOGGER.error("report execute find a error ! context is : {}" , JSON.toJSONString(reportContext) , throwable);
        }finally {
            super.applyAfter(handle,reportContext , throwable);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        super.setInterceptors(reportInterceptor);

        super.setHandles(handles);

    }
}
