package com.opencode.code.mybatis.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.mybatis.context.GeneratorContext;
import com.opencode.code.mybatis.generator.tpl.*;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * service & controller & vo & param 生成
 * */
public class OpenCodeServiceAndControllerPlugin extends PluginAdapter {

    private GeneratorContext generatorContext;

    private String doName;

    private String paramName;

    private String voName;

    private String serviceName;

    private String serviceImplName;

    private String controllerName;

    private String mapperName;

    @Override
    public boolean validate(List<String> warnings) {
        this.generatorContext = JSONObject.parseObject(JSON.toJSONString(properties), GeneratorContext.class);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        String entityName = generatorContext.getEntityName();
        this.serviceName = generatorContext.getServicePackage() + "." + entityName + "Service";
        this.serviceImplName = generatorContext.getServiceImplPackage() + "." + entityName + "ServiceImpl";
        this.controllerName=generatorContext.getControllerPackage() + "." + entityName + "Controller";
        this.doName = generatorContext.getDoPackage() + "." + entityName + "DO";
        this.paramName = generatorContext.getParamPackage() + "." + entityName + "param";
        this.voName = generatorContext.getVoPackage() + "." + entityName + "VO";
        this.mapperName = generatorContext.getDaoPackage() + "." + entityName + "Mapper";

        List<GeneratedJavaFile> answer = new ArrayList<>();

//        GeneratedJavaFile gjf = generateServiceInterface(introspectedTable);
//        GeneratedJavaFile gjf2 = generateServiceImpl(introspectedTable);
//        GeneratedJavaFile gjf3 = generateController(introspectedTable);
//        GeneratedJavaFile gjf4 = generateVO(introspectedTable);
//        GeneratedJavaFile gjf5 = generateParam(introspectedTable);

        GeneratedJavaFile gjf = new ServiceTemplate(generatorContext,context).generateServiceInterface();
        GeneratedJavaFile gjf2 = new ServiceImplTemplate(generatorContext, context).generateServiceImpl();
        GeneratedJavaFile gjf3 = new ControllerTemplate(generatorContext, context).generateController();
        GeneratedJavaFile gjf4 = new ViewObjectTemplate(generatorContext,context).generateViewObject();
        GeneratedJavaFile gjf5 = new ParamObjectTemplate(generatorContext,context).generateParamObject(introspectedTable);
        answer.add(gjf);
        answer.add(gjf2);
        answer.add(gjf3);
        answer.add(gjf4);
        answer.add(gjf5);
        return answer;
    }

    private GeneratedJavaFile generateParam(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType dataObject = new FullyQualifiedJavaType(this.doName);

        FullyQualifiedJavaType param = new FullyQualifiedJavaType(this.paramName);

        TopLevelClass clazz = new TopLevelClass(param);

        //注释
        addDoc(this.paramName.substring(this.paramName.lastIndexOf(".") + 1),clazz);

        clazz.addImportedTypes(new HashSet<>(Arrays.asList(
                new FullyQualifiedJavaType(this.doName)
        )));

        clazz.setSuperClass(dataObject);

        clazz.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(clazz,"src" + File.separator + "main" + File.separator + "java",super.context.getJavaFormatter());

    }

    private GeneratedJavaFile generateVO(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType dataObject = new FullyQualifiedJavaType(this.doName);

        FullyQualifiedJavaType vo = new FullyQualifiedJavaType(this.voName);

        TopLevelClass clazz = new TopLevelClass(vo);

        //注释
        addDoc(this.voName.substring(this.voName.lastIndexOf(".") + 1),clazz);

        clazz.addImportedTypes(new HashSet<>(Arrays.asList(
                new FullyQualifiedJavaType(this.doName)
        )));

        clazz.setSuperClass(dataObject);

        clazz.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(clazz,"src" + File.separator + "main" + File.separator + "java",super.context.getJavaFormatter());

    }

    //生成 serviceImpl 类
    private GeneratedJavaFile generateServiceImpl(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(this.serviceName);

        FullyQualifiedJavaType serviceImpl = new FullyQualifiedJavaType(this.serviceImplName);
        TopLevelClass clazz = new TopLevelClass(serviceImpl);

        //注释
        addDoc(this.serviceImplName.substring(this.serviceImplName.lastIndexOf(".") + 1),clazz);

        clazz.addSuperInterface(service);

        clazz.addAnnotation("@Service");

        clazz.addImportedTypes(new HashSet<>(Arrays.asList(
                new FullyQualifiedJavaType(this.doName),
                new FullyQualifiedJavaType(this.voName),
                new FullyQualifiedJavaType(this.paramName),
                new FullyQualifiedJavaType(this.mapperName),
                new FullyQualifiedJavaType(this.serviceName),
                new FullyQualifiedJavaType("org.springframework.stereotype.Service"),
                new FullyQualifiedJavaType("javax.annotation.Resource"),
                new FullyQualifiedJavaType("com.alibaba.fastjson.JSON"),
                new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject")
        )));

        String mName = this.mapperName.substring(this.mapperName.lastIndexOf(".") + 1);
        String mapperFieldName = firstCharToLowCase(mName);
        Field mapperField = new Field(mapperFieldName,new FullyQualifiedJavaType(mName));
        mapperField.addAnnotation("@Resource");
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        clazz.addField(mapperField);

        Method method = new Method("queryById");
        method.addAnnotation("@Override");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        method.addParameter(parameter);
        String returnDoName = this.doName.substring(this.doName.lastIndexOf(".") + 1);
        method.addBodyLine(returnDoName + " " + firstCharToLowCase(returnDoName) + " = " + mapperFieldName + ".selectByPrimaryKey(id);");
        String newVoName = this.voName.substring(this.voName.lastIndexOf(".") + 1);
        method.addBodyLine(newVoName + " " + firstCharToLowCase(newVoName) + " = JSONObject.parseObject(JSON.toJSONString(" + firstCharToLowCase(returnDoName) + "),"+ newVoName +".class);");
        method.addBodyLine("return " + firstCharToLowCase(newVoName) + ";");
        method.setReturnType(new FullyQualifiedJavaType(voName));
        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);
        clazz.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(clazz,"src" + File.separator + "main" + File.separator + "java",super.context.getJavaFormatter());
    }

    //生成 service 类
    private GeneratedJavaFile generateServiceInterface(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(this.serviceName);
        Interface serviceInterface = new Interface(service);

        //注释
        addDoc(this.serviceName.substring(this.serviceName.lastIndexOf(".") + 1),serviceInterface);

        serviceInterface.addImportedTypes(new HashSet<>(Arrays.asList(
                new FullyQualifiedJavaType(doName),
                new FullyQualifiedJavaType(this.voName),
                new FullyQualifiedJavaType(this.paramName)
        )));

        Method method = new Method("queryById");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        method.addParameter(parameter);
        method.setReturnType(new FullyQualifiedJavaType(voName.substring(voName.lastIndexOf(".") + 1)));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(serviceInterface,"src"+ File.separator +"main" + File.separator + "java",context.getJavaFormatter());
    }

    private void addDoc(String doc,JavaElement clazz){
        clazz.addJavaDocLine("/**");
        clazz.addJavaDocLine(" * " + doc);
        clazz.addJavaDocLine(" *");
        clazz.addJavaDocLine(" * @author " + generatorContext.getAuthor());
        clazz.addJavaDocLine(" * @date " + new SimpleDateFormat(generatorContext.getDateFormat()).format(new Date()));
        clazz.addJavaDocLine(" */");
    }

    private String firstCharToLowCase(String str) {
        char[] chars = new char[1];
        //String str="ABCDE1234";
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if(chars[0] >= 'A'  &&  chars[0] <= 'Z') {
            return str.replaceFirst(temp,temp.toLowerCase());
        }
        return str;
    }



}
