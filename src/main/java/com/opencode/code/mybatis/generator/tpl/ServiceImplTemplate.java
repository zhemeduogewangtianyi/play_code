package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.generator.context.GeneratorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ServiceImplTemplate extends BaseTemplate {


    public ServiceImplTemplate(GeneratorContext generatorContext, Context context, IntrospectedTable introspectedTable) {
        super(generatorContext, context, introspectedTable);
    }

    //生成 serviceImpl 类
    public GeneratedJavaFile generateServiceImpl() {

        if(StringUtils.isEmpty(super.serviceFullName) || StringUtils.isEmpty(super.serviceName) || StringUtils.isEmpty(serviceImplFullName)|| StringUtils.isEmpty(serviceImplName)){
            return null;
        }

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(this.serviceFullName);

        FullyQualifiedJavaType serviceImpl = new FullyQualifiedJavaType(this.serviceImplFullName);
        TopLevelClass clazz = new TopLevelClass(serviceImpl);

        //注释
        addDoc(this.serviceImplName,clazz,true);

        clazz.addSuperInterface(service);

        clazz.addAnnotation("@Service");

        List<FullyQualifiedJavaType> imported = new ArrayList<>();
        if(StringUtils.isNotBlank(super.voFullName)){
            imported.add(new FullyQualifiedJavaType(super.voFullName));
        }
        if(StringUtils.isNotBlank(super.paramFullName)){
            imported.add(new FullyQualifiedJavaType(super.paramFullName));
        }
        Collections.addAll(imported,
                new FullyQualifiedJavaType(super.doFullName),
                new FullyQualifiedJavaType(super.mapperFullName),
                new FullyQualifiedJavaType(super.serviceFullName),
                new FullyQualifiedJavaType("org.springframework.stereotype.Service"),
                new FullyQualifiedJavaType("javax.annotation.Resource"),
                new FullyQualifiedJavaType("com.alibaba.fastjson.JSON"),
                new FullyQualifiedJavaType("com.alibaba.fastjson.JSONObject")
                );
        clazz.addImportedTypes(new HashSet<>(imported));

        String mapperServiceName = firstCharToLowCase(super.mapperName);

        Field mapperField = new Field(mapperServiceName,new FullyQualifiedJavaType(super.mapperName));
        mapperField.addAnnotation("@Resource");
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        clazz.addField(mapperField);

        save(clazz,mapperServiceName);
        delete(clazz,mapperServiceName);
        update(clazz,mapperServiceName);
        queryById(clazz,mapperServiceName);

        clazz.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(clazz,"src" + File.separator + "main" + File.separator + "java",super.context.getJavaFormatter());
    }

    private void save(TopLevelClass clazz,String mapperServiceName) {

        Method method = new Method("save");

        method.addAnnotation("@Override");

        Parameter parameter;
        if(StringUtils.isEmpty(super.paramName)){
            parameter = new Parameter(new FullyQualifiedJavaType(super.doName),"param");
        }else{
            parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        }
        method.addParameter(parameter);

        method.addBodyLine(super.doName + " " + firstCharToLowCase(super.doName) + " = JSONObject.parseObject(JSON.toJSONString(param),"+ super.doName +".class);");
        method.addBodyLine(mapperServiceName + ".insert("+ firstCharToLowCase(super.doName) +");");
        IntrospectedColumn introspectedColumn = super.introspectedTable.getPrimaryKeyColumns().get(0);
        String javaProperty = introspectedColumn.getJavaProperty();
        method.addBodyLine("return " + firstCharToLowCase(super.doName) + ".get" + super.firstCharToUpperCase(javaProperty) + "();");

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if(CollectionUtils.isEmpty(primaryKeyColumns)){
            FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.lang.Object");
            method.setReturnType(returnType);
        }else{
            FullyQualifiedJavaType returnType = primaryKeyColumns.get(0).getFullyQualifiedJavaType();
            method.setReturnType(returnType);
        }

        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);

    }

    private void delete(TopLevelClass clazz,String mapperServiceName) {

        Method method = new Method("delete");

        method.addAnnotation("@Override");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        method.addParameter(parameter);

        method.addBodyLine("return " + mapperServiceName + ".deleteByPrimaryKey(id);");

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));
        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);

    }

    private void update(TopLevelClass clazz,String mapperServiceName) {

        Method method = new Method("update");

        method.addAnnotation("@Override");

        Parameter parameter;
        if(StringUtils.isEmpty(super.paramName)){
            parameter = new Parameter(new FullyQualifiedJavaType(super.doName),"param");
        }else{
            parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        }
        method.addParameter(parameter);

        method.addBodyLine(super.doName + " " + firstCharToLowCase(super.doName) + " = JSONObject.parseObject(JSON.toJSONString(param),"+ super.doName +".class);");
        method.addBodyLine("return " + mapperServiceName + ".updateByPrimaryKeySelective("+ firstCharToLowCase(super.doName) +");");

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));

        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);

    }

    private void queryById(TopLevelClass clazz,String mapperServiceName) {

        Method method = new Method("queryById");

        method.addAnnotation("@Override");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        method.addParameter(parameter);

        method.addBodyLine(super.doName + " " + firstCharToLowCase(super.doName) + " = " + mapperServiceName + ".selectByPrimaryKey(id);");
        if(StringUtils.isEmpty(super.voName)){
            method.addBodyLine("return " + firstCharToLowCase(super.doName) + ";");
            method.setReturnType(new FullyQualifiedJavaType(super.doName));
        }else{
            method.addBodyLine("return JSONObject.parseObject(JSON.toJSONString(" + firstCharToLowCase(super.doName) + "),"+ super.voName +".class);");
            method.setReturnType(new FullyQualifiedJavaType(super.voName));
        }

        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);

    }

}
