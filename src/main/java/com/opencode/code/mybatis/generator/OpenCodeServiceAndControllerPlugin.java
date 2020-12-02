package com.opencode.code.mybatis.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.mybatis.context.GeneratorContext;
import com.opencode.code.mybatis.generator.tpl.*;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * service & controller & vo & param
 * */
public class OpenCodeServiceAndControllerPlugin extends PluginAdapter {

    private GeneratorContext generatorContext;

    @Override
    public boolean validate(List<String> warnings) {
        this.generatorContext = JSONObject.parseObject(JSON.toJSONString(properties), GeneratorContext.class);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        List<GeneratedJavaFile> answer = new ArrayList<>();

        GeneratedJavaFile gjf = new ServiceTemplate(this.generatorContext,super.context,introspectedTable).generateServiceInterface();
        GeneratedJavaFile gjf2 = new ServiceImplTemplate(this.generatorContext, super.context,introspectedTable).generateServiceImpl();
        GeneratedJavaFile gjf3 = new ControllerTemplate(this.generatorContext, super.context,introspectedTable).generateController();
        GeneratedJavaFile gjf4 = new ViewObjectTemplate(this.generatorContext,super.context,introspectedTable).generateViewObject();
        GeneratedJavaFile gjf5 = new ParamObjectTemplate(this.generatorContext,super.context,introspectedTable).generateParamObject();

        answer.add(gjf);
        answer.add(gjf2);
        answer.add(gjf3);
        answer.add(gjf4);
        answer.add(gjf5);
        return answer;
    }

}
