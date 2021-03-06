package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.generator.context.GeneratorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseTemplate {

    protected static final String DEFAULT_RETURN_TYPE = "java.lang.Object";

    protected final GeneratorContext generatorContext;

    protected final Context context;

    protected IntrospectedTable introspectedTable;

    protected String doName;

    protected String doFullName;

    protected String paramName;

    protected String paramFullName;

    protected String voName;

    protected String voFullName;

    protected String serviceName;

    protected String serviceFullName;

    protected String serviceImplName;

    protected String serviceImplFullName;

    protected String controllerName;

    protected String controllerFullName;

    protected String mapperName;

    protected String mapperFullName;

    public BaseTemplate(GeneratorContext generatorContext, Context context,IntrospectedTable introspectedTable) {
        this.generatorContext = generatorContext;
        this.context = context;
        this.introspectedTable = introspectedTable;

        String entityName = generatorContext.getEntityName();

        if(StringUtils.isNotBlank(generatorContext.getDoPackage())){
            this.doName = entityName + "DO";
            this.doFullName = generatorContext.getDoPackage() + "." + entityName + "DO";
        }

        if(StringUtils.isNotBlank(generatorContext.getParamPackage())){
            this.paramName = entityName + "Param";
            this.paramFullName = generatorContext.getParamPackage() + "." + entityName + "Param";
        }

        if(StringUtils.isNotBlank(generatorContext.getVoPackage())){
            this.voName = entityName + "VO";
            this.voFullName = generatorContext.getVoPackage() + "." + entityName + "VO";
        }

        if(StringUtils.isNotBlank(generatorContext.getServicePackage())){
            this.serviceName = entityName + "Service";
            this.serviceFullName = generatorContext.getServicePackage() + "." + entityName + "Service";
        }

        if(StringUtils.isNotBlank(generatorContext.getServiceImplPackage())){
            this.serviceImplName = entityName + "ServiceImpl";
            this.serviceImplFullName = generatorContext.getServiceImplPackage() + "." + entityName + "ServiceImpl";
        }

        if(StringUtils.isNotBlank(generatorContext.getControllerPackage())){
            this.controllerName = entityName + "Controller";
            this.controllerFullName = generatorContext.getControllerPackage() + "." + entityName + "Controller";
        }

        if(StringUtils.isNotBlank(generatorContext.getDaoPackage())){
            this.mapperName = entityName + "Mapper";
            this.mapperFullName = generatorContext.getDaoPackage() + "." + entityName + "Mapper";
        }

    }

    protected void addDoc(String doc, JavaElement clazz,boolean needInfo){
        clazz.addJavaDocLine("/**");
        clazz.addJavaDocLine(" * " + doc);
        if(needInfo){
            clazz.addJavaDocLine(" *");
            clazz.addJavaDocLine(" * @author " + generatorContext.getAuthor());
            clazz.addJavaDocLine(" * @date " + new SimpleDateFormat(generatorContext.getDateFormat()).format(new Date()));
        }

        clazz.addJavaDocLine(" */");
    }

    protected String firstCharToLowCase(String str) {
        char[] chars = new char[1];
        //String str="ABCDE1234";
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if(chars[0] >= 'A'  &&  chars[0] <= 'Z') {
            return str.replaceFirst(temp,temp.toLowerCase());
        }
        return str;
    }

    protected String firstCharToUpperCase(String str) {
        char[] chars = new char[1];
        //String str="ABCDE1234";
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if(chars[0] >= 'a'  &&  chars[0] <= 'z') {
            return str.replaceFirst(temp,temp.toUpperCase());
        }
        return str;
    }

    protected void objectGenerator(InnerClass clazz, IntrospectedTable introspectedTable){

        List<IntrospectedColumn> columns = new ArrayList<>();
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        List<IntrospectedColumn> blobColumns = introspectedTable.getBLOBColumns();

        if(!CollectionUtils.isEmpty(primaryKeyColumns)){
            columns.addAll(primaryKeyColumns);
        }

        if(!CollectionUtils.isEmpty(baseColumns)){
            columns.addAll(baseColumns);
        }

        if(!CollectionUtils.isEmpty(blobColumns)){
            columns.addAll(blobColumns);
        }

        for(IntrospectedColumn isc : columns){

            String remarks = isc.getRemarks();
            String javaProperty = isc.getJavaProperty();
            FullyQualifiedJavaType fullyQualifiedJavaType = isc.getFullyQualifiedJavaType();
            Field field = new Field(javaProperty,fullyQualifiedJavaType);
            field.setVisibility(JavaVisibility.PRIVATE);

            addDoc(remarks,field,false);

            clazz.addField(field);
        }
    }

}
