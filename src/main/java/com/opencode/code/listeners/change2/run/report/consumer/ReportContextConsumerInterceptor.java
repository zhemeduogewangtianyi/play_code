package com.opencode.code.listeners.change2.run.report.consumer;

import com.alibaba.fastjson.JSONArray;
import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.context.etl.EtlContext;
import com.opencode.code.listeners.change2.entity.param.EtlFilterParam;
import com.opencode.code.listeners.change2.entity.param.EtlMappingParam;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.enums.DataProcessorStatusEnum;
import com.opencode.code.listeners.change2.interfaces.Handle;
import com.opencode.code.listeners.change2.interfaces.Interceptor;
import com.opencode.code.listeners.change2.mock.Redis;
import com.opencode.code.velocity.VelocityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Component
public class ReportContextConsumerInterceptor implements Interceptor<ReportContext> {

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
        if(context.getDataSource() == null){
            return "datasource config can't null !";
        }
        if(StringUtils.isEmpty(context.getDir())){
            return "cache dir can't null !";
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
        List<Map<String, Object>> datas = (List<Map<String, Object>>) result;
        EtlContext etlContext = context.getEtlContext();
        String filterMapping = etlContext.getFilterMapping();
        List<EtlFilterParam> etlFilterParams = JSONArray.parseArray(filterMapping, EtlFilterParam.class);
        String etlMapping = etlContext.getEtlMapping();
        List<EtlMappingParam> etlMappingParams = JSONArray.parseArray(etlMapping, EtlMappingParam.class);

        for(EtlFilterParam ef : etlFilterParams){
            for(Map<String,Object> data : datas){
                data.remove(ef.getFilter());
            }
        }

        for(Map<String,Object> data : datas){
            for(EtlMappingParam em : etlMappingParams){
                if(data.containsKey(em.getSource())){
                    Object value = data.get(em.getSource());
                    data.remove(em.getSource());
                    data.put(em.getTarget(),value);
                }
            }
        }



        Map<String,Object> tplMap = new HashMap<>();
        tplMap.put("datas",datas);

        String content = VelocityUtils.generator(context.getTemplateContext().getTemplate(), tplMap);
        String fileName = VelocityUtils.generator(context.getFileName(), tplMap);
        context.setFileName(fileName);

        //TODO 假装上传到 OSS
        String dir = context.getDir();
        String path = dir + File.separator + fileName;
        File mkDirFile = new File(dir);
        if(!mkDirFile.exists()){
            mkDirFile.mkdirs();
        }
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file,false);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        bos.write(content.getBytes());

        bos.close();
        fos.close();

    }

    @Override
    public Object after(Handle<ReportContext> handle, ReportContext context, Throwable e) throws Throwable {
        if(e == null){
            //TODO 落库、更新
            Redis.push(DataProcessorNodeEnum.CONSUMER.getSender() + context.getTaskId() , context);
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
        if(!DataProcessorNodeEnum.CONSUMER.getType().equals(reportContext.getNode())){
            return false;
        }
        if(reportContext.getTaskId() == null){
            return false;
        }
        return true;
    }
}
