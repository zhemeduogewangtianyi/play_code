package com.opencode.code.mybatis.generator.tpl;

import com.opencode.code.mybatis.context.GeneratorContext;
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

    public ParamObjectTemplate(GeneratorContext generatorContext, Context context) {
        super(generatorContext, context);
    }

    public GeneratedJavaFile generateParamObject(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType dataObject = new FullyQualifiedJavaType(this.doFullName);

        FullyQualifiedJavaType param = new FullyQualifiedJavaType(this.paramFullName);

        TopLevelClass clazz = new TopLevelClass(param);

        //注释
        addDoc(this.paramName,clazz);

        clazz.addImportedTypes(new HashSet<>(Collections.singletonList(
                new FullyQualifiedJavaType(this.doName)
        )));

        clazz.setSuperClass(dataObject);

        clazz.setVisibility(JavaVisibility.PUBLIC);

        return new GeneratedJavaFile(clazz,"src" + File.separator + "main" + File.separator + "java",super.context.getJavaFormatter());

    }

}
