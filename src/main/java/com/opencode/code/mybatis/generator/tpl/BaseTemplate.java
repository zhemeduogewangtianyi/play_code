package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.context.GeneratorContext;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.config.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTemplate {

    protected static final String DEFAULT_RETURN_TYPE = "java.lang.Object";

    protected final GeneratorContext generatorContext;

    protected final Context context;

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

    public BaseTemplate(GeneratorContext generatorContext, Context context) {
        this.generatorContext = generatorContext;
        this.context = context;
        String entityName = generatorContext.getEntityName();

        this.doName = entityName + "DO";
        this.doFullName = generatorContext.getDoPackage() + "." + entityName + "DO";

        this.paramName = entityName + "Param";
        this.paramFullName = generatorContext.getParamPackage() + "." + entityName + "Param";

        this.voName = entityName + "VO";
        this.voFullName = generatorContext.getVoPackage() + "." + entityName + "VO";

        this.serviceName = entityName + "Service";
        this.serviceFullName = generatorContext.getServicePackage() + "." + entityName + "Service";

        this.serviceImplName = entityName + "ServiceImpl";
        this.serviceImplFullName = generatorContext.getServiceImplPackage() + "." + entityName + "ServiceImpl";

        this.controllerName = entityName + "Controller";
        this.controllerFullName = generatorContext.getControllerPackage() + "." + entityName + "Controller";

        this.mapperName = entityName + "Mapper";
        this.mapperFullName = generatorContext.getDaoPackage() + "." + entityName + "Mapper";

    }

    protected void addDoc(String doc, JavaElement clazz){
        clazz.addJavaDocLine("/**");
        clazz.addJavaDocLine(" * " + doc);
        clazz.addJavaDocLine(" *");
        clazz.addJavaDocLine(" * @author " + generatorContext.getAuthor());
        clazz.addJavaDocLine(" * @date " + new SimpleDateFormat(generatorContext.getDateFormat()).format(new Date()));
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

}
