package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.context.GeneratorContext;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class ServiceTemplate extends BaseTemplate{

    public ServiceTemplate(GeneratorContext generatorContext, Context context) {
        super(generatorContext, context);
    }

    //生成 service 类
    public GeneratedJavaFile generateServiceInterface() {

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(super.serviceFullName);

        Interface serviceInterface = new Interface(service);

        //注释
        addDoc(super.serviceName,serviceInterface,true);

        serviceInterface.addImportedTypes(new HashSet<>(Arrays.asList(
                new FullyQualifiedJavaType(super.voFullName),
                new FullyQualifiedJavaType(super.paramFullName)
        )));

        save(serviceInterface);
        delete(serviceInterface);
        update(serviceInterface);
        queryById(serviceInterface);

        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(serviceInterface,"src"+ File.separator +"main" + File.separator + "java",context.getJavaFormatter());
    }

    private void save(Interface serviceInterface) {

        Method method = new Method("save");

        addDoc("save",method,true);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Long"));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }

    private void delete(Interface serviceInterface) {

        Method method = new Method("delete");

        addDoc("delete",method,true);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }

    private void update(Interface serviceInterface) {

        Method method = new Method("update");

        addDoc("update",method,true);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }

    private void queryById(Interface serviceInterface) {

        Method method = new Method("queryById");

        addDoc("queryById",method,true);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType(super.voFullName));

        serviceInterface.addMethod(method);
        serviceInterface.setVisibility(JavaVisibility.DEFAULT);
    }


}
