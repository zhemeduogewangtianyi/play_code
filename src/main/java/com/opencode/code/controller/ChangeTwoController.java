package com.opencode.code.controller;

import com.opencode.code.listeners.change2.context.ReportContext;
import com.opencode.code.listeners.change2.enums.DataProcessorNodeEnum;
import com.opencode.code.listeners.change2.mock.Redis;
import com.opencode.code.listeners.change2.run.report.ReportExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeTwoController {

    @Autowired
    private ReportExecute execute;

    // http://localhost:8080/changeProducer?taskId=1
    @RequestMapping("/changeProducer")
    public Object changeProducer(@RequestParam Long taskId){
        ReportContext context = new ReportContext();
        context.setTaskId(taskId);
        context.setNode(DataProcessorNodeEnum.PRODUCER.getType());
        execute.execute(context);
        return "success";
    }

    // http://localhost:8080/changeConsumer?taskId=1
    @RequestMapping("/changeConsumer")
    public Object changeConsumer(@RequestParam Long taskId){

        ReportContext context = new ReportContext();
        context.setTaskId(taskId);
        context.setNode(DataProcessorNodeEnum.CONSUMER.getType());
        execute.execute(context);
        return "success";
    }

    // http://localhost:8080/changePush?taskId=1
    @RequestMapping("/changePush")
    public Object changePush(@RequestParam Long taskId){

        ReportContext context = new ReportContext();
        context.setTaskId(taskId);
        context.setNode(DataProcessorNodeEnum.PUSH.getType());
        execute.execute(context);
        return "success";
    }


}
