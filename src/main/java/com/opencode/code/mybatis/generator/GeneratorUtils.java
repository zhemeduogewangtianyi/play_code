package com.opencode.code.mybatis.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.mybatis.context.GeneratorContext;
import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GeneratorUtils {

    //generator
    public void generator(GeneratorContext gc) {
        List<String> warnings = new ArrayList<>();
        try {

            Configuration config = configuration(gc);
            //是否覆盖
            DefaultShellCallback dsc = new DefaultShellCallback(true);
            MyBatisGenerator mg = new MyBatisGenerator(config, dsc, warnings);
            mg.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Configuration
    private Configuration configuration(GeneratorContext generatorContext) {

        Configuration cfg = new Configuration();

        String mysqlJarPath = generatorContext.getMysqlJarPath();
        if(mysqlJarPath != null && !"".equals(mysqlJarPath.trim())){
            cfg.addClasspathEntry(mysqlJarPath);
        }

        //disable WithBLOBS file，flat in to java class
        Context context = new Context(ModelType.FLAT);

        context.setId("default");

        //sourceCode default is MyBatis3
        context.setTargetRuntime("com.opencode.code.mybatis.generator.OpenCodeIntrospectedTable");

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
        javaClientGeneratorConfiguration.setTargetPackage(generatorContext.getDaoPackage());
        javaClientGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        //file path - mapper
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(targetResources);
        sqlMapGeneratorConfiguration.setTargetPackage(generatorContext.getMapperPackage());
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        //file path - entity
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(targetProject);
        javaModelGeneratorConfiguration.setTargetPackage(generatorContext.getDoPackage());
        javaModelGeneratorConfiguration.addProperty("immutable","false");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","false");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        javaModelGeneratorConfiguration.addProperty("constructorBased","true");
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
            pluginConfiguration.setConfigurationType("com.opencode.code.mybatis.generator.OpenCodeServiceAndControllerPlugin");
            String gcJson = JSON.toJSONString(generatorContext);
            Map map = JSONObject.parseObject(gcJson, Map.class);
            for(Iterator car = map.entrySet().iterator() ; car.hasNext() ; ){
                Map.Entry next = (Map.Entry) car.next();
                Object key = next.getKey();
                Object value = next.getValue();
                pluginConfiguration.addProperty(key.toString(),value.toString());
            }
            context.addPluginConfiguration(pluginConfiguration);
        }

        //generator comment settings
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.opencode.code.mybatis.generator.OpenCodeCommentGenerator");
        commentGeneratorConfiguration.addProperty("dateFormat",dateFormat);
        commentGeneratorConfiguration.addProperty("author",author);
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        cfg.addContext(context);
        return cfg;
    }

    public static void main(String[] args) {
        GeneratorContext gc = new GeneratorContext();

//        gc.setMysqlJarPath("mysql-connector-java-8.0.21.jar");
//        gc.setConnectionUrl("jdbc:mysql://192.168.3.10:3306/dddxhh");

        //jdbc databases Configuration
        gc.setConnectionUrl("jdbc:mysql://192.168.33.10:3306/dddxhh");
        gc.setDriverClass("com.mysql.cj.jdbc.Driver");
        gc.setUsername("dddxhh");
        gc.setPassword("123456");
        gc.setAuthor("DDDXHH");

        //name settings
        gc.setTableName("ali_xchange_data_reflow");
        gc.setEntityName("AliXchangeDataReflow");

        //base mapper package path
        gc.setDoPackage("com.opencode.code.entity");
        gc.setDaoPackage("com.opencode.code.dao");
        gc.setMapperPackage("mapper");

        //extern service & serviceImpl & controller & ViewObject & ParamObject path
        gc.setServicePackage("com.opencode.code.mybatis.service");
        gc.setServiceImplPackage("com.opencode.code.mybatis.service.impl");
        gc.setControllerPackage("com.opencode.code.mybatis.controller");
        gc.setParamPackage("com.opencode.code.entity.param");
        gc.setVoPackage("com.opencode.code.entity.vo");

        //primaryKey
        gc.setPrimaryKey("id");

        //run
        new GeneratorUtils().generator(gc);
    }

}
