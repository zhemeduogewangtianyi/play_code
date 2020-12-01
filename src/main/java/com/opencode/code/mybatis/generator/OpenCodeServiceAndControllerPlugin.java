package com.opencode.code.mybatis.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.mybatis.context.GeneratorContext;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * service & controller 生成
 * */
public class OpenCodeServiceAndControllerPlugin extends PluginAdapter {

    private GeneratorContext generatorContext;

    private String serviceName;

    private String serviceImplName;

    private String controllerName;

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
        List<GeneratedJavaFile> answer = new ArrayList<>();
//        GeneratedJavaFile gjf = generateServiceInterface(introspectedTable);
//        GeneratedJavaFile gjf2 = generateServiceImpl(introspectedTable);
        GeneratedJavaFile gjf3 = generateController(introspectedTable);
//        answer.add(gjf);
//        answer.add(gjf2);
        answer.add(gjf3);
        return answer;
    }

    // 生成controller类
    private GeneratedJavaFile generateController(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType controller = new FullyQualifiedJavaType(this.controllerName);
        TopLevelClass clazz = new TopLevelClass(controller);
        //描述类的作用域修饰符
        clazz.setVisibility(JavaVisibility.PUBLIC);

        //添加@Controller注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        clazz.addAnnotation("@RestController");
        //添加@RequestMapping注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        clazz.addAnnotation("@RequestMapping(\"/" + "api/"+firstCharToLowCase(generatorContext.getEntityName())+"\")");


        //引入Service
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(serviceName);
        clazz.addImportedType(service);

        //添加Service成员变量
        String serviceFieldName = firstCharToLowCase(serviceName.substring(serviceName.lastIndexOf(".") + 1));
        Field daoField = new Field(serviceFieldName, new FullyQualifiedJavaType(serviceName));
        clazz.addImportedType(new FullyQualifiedJavaType(serviceName));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        //描述成员属性 的注解
        daoField.addAnnotation("@Autowired");
        //描述成员属性修饰符
        daoField.setVisibility(JavaVisibility.PRIVATE);
        clazz.addField(daoField);


        //描述 方法名
        Method method = new Method("queryById");
        //方法注解
        method.addAnnotation("@RequestMapping(\"/queryById\")");
        TypeParameter typeParameter = new TypeParameter("id", Collections.singletonList(new FullyQualifiedJavaType("java.lang.Long")));
        method.addTypeParameter(typeParameter);
//        String simpleSuperServiceName = superServiceInterface.substring(superServiceInterface.lastIndexOf(".") + 1);
//        FullyQualifiedJavaType methodReturnType = new FullyQualifiedJavaType(simpleSuperServiceName+"<"+modelName+">");
//        //返回类型
//        method.setReturnType(methodReturnType);
//        //方法体，逻辑代码
//        method.addBodyLine("return " + serviceFieldName + ";");
//        //修饰符
//        method.setVisibility(JavaVisibility.PUBLIC);
//        clazz.addImportedType(superServiceInterface);
//        clazz.addMethod(method);


        return new GeneratedJavaFile(clazz, "src"+ File.separator +"main" + File.separator + "java", context.getJavaFormatter());

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
