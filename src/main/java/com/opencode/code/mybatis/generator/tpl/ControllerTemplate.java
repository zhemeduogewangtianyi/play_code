package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.context.GeneratorContext;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class ControllerTemplate extends BaseTemplate {

    private Context context;

    public ControllerTemplate(GeneratorContext generatorContext,Context context) {
        super(generatorContext,context);
    }

    // 生成 controller 类
    public GeneratedJavaFile generateController() {

        FullyQualifiedJavaType controller = new FullyQualifiedJavaType(super.controllerFullName);
        TopLevelClass clazz = new TopLevelClass(controller);

        //注释
        addDoc(super.controllerName,clazz,true);

        //描述类的作用域修饰符
        clazz.setVisibility(JavaVisibility.PUBLIC);

        //添加@Controller注解，并引入相应的类
        clazz.addImportedTypes(new HashSet<>(Arrays.asList(
                new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"),
                new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"),
                new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"),
                new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestBody"),
                new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestParam"),
                //引入Service
                new FullyQualifiedJavaType(super.serviceFullName),
                new FullyQualifiedJavaType(super.voFullName),
                new FullyQualifiedJavaType(super.paramFullName)
        )));

        clazz.addAnnotation("@RestController");
        //添加@RequestMapping注解，并引入相应的类
        clazz.addAnnotation("@RequestMapping(value = \"/" + "api/"+firstCharToLowCase(super.generatorContext.getEntityName())+"\")");

        //添加Service成员变量
        String serviceFieldName = firstCharToLowCase(super.serviceName);
        Field daoField = new Field(serviceFieldName, new FullyQualifiedJavaType(super.serviceName));
        //描述成员属性 的注解
        daoField.addAnnotation("@Autowired");
        //描述成员属性修饰符
        daoField.setVisibility(JavaVisibility.PRIVATE);
        clazz.addField(daoField);

        save(clazz,serviceFieldName);
        delete(clazz,serviceFieldName);
        update(clazz,serviceFieldName);
        queryById(clazz,serviceFieldName);

        return new GeneratedJavaFile(clazz, "src"+ File.separator +"main" + File.separator + "java", super.context.getJavaFormatter());

    }


    // 生成 controller save
    private void save(TopLevelClass clazz,String serviceFieldName){
        Method method = new Method("save");

        method.addAnnotation("@RequestMapping(value = \"/save\")");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        parameter.addAnnotation("@RequestBody");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType(DEFAULT_RETURN_TYPE));

        method.addBodyLine("return " + serviceFieldName + ".save(param);");

        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);
    }

    // 生成 controller delete
    private void delete(TopLevelClass clazz,String serviceFieldName) {

        Method method = new Method("delete");

        method.addAnnotation("@RequestMapping(value = \"/delete\")");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        parameter.addAnnotation("@RequestParam(name = \"id\")");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType(DEFAULT_RETURN_TYPE));

        method.addBodyLine("return " + serviceFieldName + ".delete(id);");

        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);

    }

    // 生成 controller update
    private void update(TopLevelClass clazz,String serviceFieldName){

        Method method = new Method("update");

        method.addAnnotation("@RequestMapping(value = \"/update\")");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(super.paramName),"param");
        parameter.addAnnotation("@RequestBody");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType(DEFAULT_RETURN_TYPE));

        method.addBodyLine("return " + serviceFieldName + ".update(param);");

        method.setVisibility(JavaVisibility.PUBLIC);

        clazz.addMethod(method);
    }

    // 生成 controller queryById
    private void queryById(TopLevelClass clazz,String serviceFieldName) {

        Method method = new Method("queryById");

        method.addAnnotation("@RequestMapping(value = \"/queryById\")");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType("java.lang.Long"),"id");
        parameter.addAnnotation("@RequestParam(name = \"id\")");
        method.addParameter(parameter);

        method.setReturnType(new FullyQualifiedJavaType(DEFAULT_RETURN_TYPE));

        method.addBodyLine("return " + serviceFieldName + ".queryById(id);");

        method.setVisibility(JavaVisibility.PUBLIC);
        clazz.addMethod(method);

    }



}
