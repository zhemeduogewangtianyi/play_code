package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.generator.context.GeneratorContext;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;

public class ParamObjectTemplate extends BaseTemplate {


    public ParamObjectTemplate(GeneratorContext generatorContext, Context context, IntrospectedTable introspectedTable) {
        super(generatorContext, context, introspectedTable);
    }

    public GeneratedJavaFile generateParamObject() {

        if(StringUtils.isEmpty(super.paramFullName) || StringUtils.isEmpty(super.paramName)){
            return null;
        }

        FullyQualifiedJavaType param = new FullyQualifiedJavaType(super.paramFullName);

        TopLevelClass clazz = new TopLevelClass(param);

        //注释
        addDoc(super.paramName,clazz,true);

        clazz.addImportedTypes(new HashSet<>(Collections.singletonList(
                new FullyQualifiedJavaType("lombok.Data")
        )));

        clazz.addAnnotation("@Data");

        super.objectGenerator(clazz,super.introspectedTable);

        clazz.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(clazz,"src" + File.separator + "main" + File.separator + "java",super.context.getJavaFormatter());

    }

}
