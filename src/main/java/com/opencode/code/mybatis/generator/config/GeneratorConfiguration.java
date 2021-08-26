package com.opencode.code.mybatis.generator.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.mybatis.generator.context.GeneratorContext;
import com.opencode.code.mybatis.generator.extern.OpenCodeCommentGenerator;
import com.opencode.code.mybatis.generator.extern.OpenCodeIntrospectedTable;
import com.opencode.code.mybatis.generator.extern.OpenCodeServiceAndControllerPlugin;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.config.*;

import java.util.Iterator;
import java.util.Map;

public class GeneratorConfiguration {

    //Configuration
    public Configuration configuration(GeneratorContext generatorContext){

        Configuration cfg = new Configuration();

        String mysqlJarPath = generatorContext.getMysqlJarPath();
        if(mysqlJarPath != null && !"".equals(mysqlJarPath.trim())){
            cfg.addClasspathEntry(mysqlJarPath);
        }

        //disable WithBLOBS file，flat in to java class
        Context context = new Context(ModelType.FLAT);

        context.setId("default");

        //sourceCode default is MyBatis3
        context.setTargetRuntime(OpenCodeIntrospectedTable.class.getName());

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(generatorContext.getConnectionUrl());
        jdbcConnectionConfiguration.setDriverClass(generatorContext.getDriverClass());
        jdbcConnectionConfiguration.setUserId(generatorContext.getUsername());
        jdbcConnectionConfiguration.setPassword(generatorContext.getPassword());
        jdbcConnectionConfiguration.addProperty("useInformationSchema","true");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty("forceBigDecimals","false");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        String targetResources = generatorContext.getTargetResources();
        String targetProject = generatorContext.getTargetProject();

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();

        //file path - dao
        javaClientGeneratorConfiguration.setTargetProject(targetProject);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        String daoPackage = generatorContext.getDaoPackage();
        if(StringUtils.isEmpty(daoPackage)){
            throw new RuntimeException("daoPackage not null !");
        }
        javaClientGeneratorConfiguration.setTargetPackage(daoPackage);
        javaClientGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        //file path - mapper
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(targetResources);
        String mapperPackage = generatorContext.getMapperPackage();
        if(StringUtils.isEmpty(mapperPackage)){
            throw new RuntimeException("mapperPackage not null !");
        }
        sqlMapGeneratorConfiguration.setTargetPackage(mapperPackage);
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        //file path - entity
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(targetProject);
        String doPackage = generatorContext.getDoPackage();
        if(StringUtils.isEmpty(doPackage)){
            throw new RuntimeException("doPackage not null !");
        }
        javaModelGeneratorConfiguration.setTargetPackage(doPackage);
        javaModelGeneratorConfiguration.addProperty("immutable","false");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","false");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        javaModelGeneratorConfiguration.addProperty("constructorBased","false");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);


        //data table configuration
        TableConfiguration tableConfiguration = new TableConfiguration(context);

        tableConfiguration.setInsertStatementEnabled(true);
        tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setSelectByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(true);

        //primaryKey return to insert Object
        GeneratedKey generatedKey = new GeneratedKey(generatorContext.getPrimaryKey(),"JDBC",true,"post");
        tableConfiguration.setGeneratedKey(generatedKey);

        tableConfiguration.setSelectByExampleStatementEnabled(false);
        tableConfiguration.setCountByExampleStatementEnabled(false);
        tableConfiguration.setDeleteByExampleStatementEnabled(false);
        tableConfiguration.setWildcardEscapingEnabled(false);
        tableConfiguration.setSelectByExampleQueryId("false");
        tableConfiguration.setUpdateByExampleStatementEnabled(false);
        tableConfiguration.setAllColumnDelimitingEnabled(false);
        tableConfiguration.setDelimitIdentifiers(false);

        // name configuration
        String tableName = generatorContext.getTableName();
        String entityName = generatorContext.getEntityName();

        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(entityName);

        context.addTableConfiguration(tableConfiguration);

        //comment variable
        String dateFormat = generatorContext.getDateFormat();
        String author = generatorContext.getAuthor();

        //extern generator service & serviceImpl & controller & ViewObject & ParamObject
        String servicePackage = generatorContext.getServicePackage();
        String serviceImplPackage = generatorContext.getServiceImplPackage();
        String controllerPackage = generatorContext.getControllerPackage();

        if(StringUtils.isNotBlank(servicePackage) && StringUtils.isNotBlank(serviceImplPackage) && StringUtils.isNotBlank(controllerPackage)){
            //generator
            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType(OpenCodeServiceAndControllerPlugin.class.getName());
            String gcJson = JSON.toJSONString(generatorContext);
            Map map = JSONObject.parseObject(gcJson, Map.class);
            for(Iterator car = map.entrySet().iterator(); car.hasNext() ; ){
                Map.Entry next = (Map.Entry) car.next();
                Object key = next.getKey();
                Object value = next.getValue();
                pluginConfiguration.addProperty(key.toString(),value.toString());
            }
            context.addPluginConfiguration(pluginConfiguration);
        }

        //generator comment settings
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType(OpenCodeCommentGenerator.class.getName());
        commentGeneratorConfiguration.addProperty("dateFormat",dateFormat);
        commentGeneratorConfiguration.addProperty("author",author);
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        cfg.addContext(context);
        return cfg;
    }

}
