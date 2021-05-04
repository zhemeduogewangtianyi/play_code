package com.opencode.code.listeners.change2.service.impl;

import com.opencode.code.listeners.change2.entity.daoObject.TaskDO;
import com.opencode.code.listeners.change2.enums.TaskTypeEnum;
import com.opencode.code.listeners.change2.service.TaskService;
import org.springframework.stereotype.Component;

@Component
public class TaskServiceImpl implements TaskService {

    @Override
    public TaskDO queryById(Long id) {
        TaskDO taskDO = new TaskDO();
        taskDO.setId(id);
        taskDO.setDataSourceId(1L);
        taskDO.setEtlId(1L);
        taskDO.setTemplateId(1L);
        taskDO.setDeliveryId(1L);
        taskDO.setType(TaskTypeEnum.REPORT.getType());
        taskDO.setStatus(1);

        taskDO.setStep(1);
        taskDO.setReportCnt(10);
        return taskDO;
    }

}
