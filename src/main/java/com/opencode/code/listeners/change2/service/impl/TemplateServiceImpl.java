package com.opencode.code.listeners.change2.service.impl;

import com.opencode.code.listeners.change2.entity.daoObject.TemplateDO;
import com.opencode.code.listeners.change2.service.TemplateService;
import org.springframework.stereotype.Component;

@Component
public class TemplateServiceImpl implements TemplateService {

    @Override
    public TemplateDO queryById(Long id) {

        TemplateDO templateDO = new TemplateDO();
        templateDO.setId(id);
        templateDO.setStatus(1);
        templateDO.setName("模板渲染");
        templateDO.setNeedZip(0);
        templateDO.setZipPassword("123456");
        templateDO.setTemplate("hello $!{datas[0].A} - $!{datas[0].B} - $!{datas[0].C}");
        templateDO.setFileName("NAME-$!{datas[0].A}-$!{datas[0].B}-$!{datas[0].C}.txt");

        return templateDO;
    }

}
