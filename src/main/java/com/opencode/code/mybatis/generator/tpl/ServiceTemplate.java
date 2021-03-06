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
import java.util.HashSet;
import java.util.List;

public class ServiceTemplate extends BaseTemplate{


    public ServiceTemplate(GeneratorContext generatorContext, Context context, IntrospectedTable introspectedTable) {
        super(generatorContext, context, introspectedTable);
    }

    //生成 service 类
    public GeneratedJavaFile generateServiceInterface() {


        if(StringUtils.isEmpty(super.serviceFullName) || StringUtils.isEmpty(super.serviceName)){
            return null;
        }

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(super.serviceFullName);

        Interface serviceInterface = new Interface(service);

        //注释
        addDoc(super.serviceName,serviceInterface,true);

        List<FullyQualifiedJavaType> imported = new ArrayList<>();
        if(StringUtils.isEmpty(super.voFullName)){
            imported.add(new FullyQualifiedJavaType(super.doFullName));
        }else{
            imported.add(new FullyQualifiedJavaType(super.voFullName));
        }
        if(StringUtils.isEmpty(super.paramFullName)){
            imported.add(new FullyQualifiedJavaType(super.doFullName));
        }else{
            imported.add(new FullyQualifiedJavaType(super.paramFullName));
        }

        serviceInterface.addImportedTypes(new HashSet<>(imported));

        save(serviceInterface,super.introspectedTable);
        delete(serviceInterface);
        update(serviceInterface);
        queryById(serviceInterface);

        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(serviceInterface,"src"+ File.separator +"main" + File.separator + "java",context.getJavaFormatter());
    }

    private void save(Interface serviceInterface,IntrospectedTable introspectedTable) {

        Method method = new Method("save");

        addDoc("save",method,true);

        Parameter parameter;
        if(StringUtils.isEmpty(super.paramName)){
            parameter = new Parameter(new FullyQualifiedJavaType(super.doName),"param");
        }else{
            parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        }
        method.addParameter(parameter);

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if(CollectionUtils.isEmpty(primaryKeyColumns)){
            FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.lang.Object");
            method.setReturnType(returnType);
        }else{
            FullyQualifiedJavaType returnType = primaryKeyColumns.get(0).getFullyQualifiedJavaType();
            method.setReturnType(returnType);
        }

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }

    private void delete(Interface serviceInterface) {

        Method method = new Method("delete");

        addDoc("delete",method,true);

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        FullyQualifiedJavaType paramType = primaryKeyColumns.get(0).getFullyQualifiedJavaType();
        Parameter parameter = new Parameter(paramType,"id");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }

    private void update(Interface serviceInterface) {

        Method method = new Method("update");

        addDoc("update",method,true);

        Parameter parameter;
        if(StringUtils.isEmpty(super.paramName)){
            parameter = new Parameter(new FullyQualifiedJavaType(super.doName),"param");
        }else{
            parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        }
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }

    private void queryById(Interface serviceInterface) {

        Method method = new Method("queryById");

        addDoc("queryById",method,true);

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        FullyQualifiedJavaType paramType = primaryKeyColumns.get(0).getFullyQualifiedJavaType();
        Parameter parameter = new Parameter(paramType,"id");
        method.addParameter(parameter);

        if(StringUtils.isEmpty(super.voFullName)){
            method.setReturnType(new FullyQualifiedJavaType(super.doName));
        }else{
            method.setReturnType(new FullyQualifiedJavaType(super.voName));
        }


        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }


}
