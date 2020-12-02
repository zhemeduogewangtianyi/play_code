package com.opencode.code.mybatis.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.mybatis.context.GeneratorContext;
import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class GeneratorUtils {

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

    private Configuration configuration(GeneratorContext generatorContext) {

        Configuration cfg = new Configuration();

        String mysqlJarPath = generatorContext.getMysqlJarPath();
        if(mysqlJarPath != null && !"".equals(mysqlJarPath.trim())){
            cfg.addClasspathEntry(mysqlJarPath);
        }

        //禁止 WithBLOBS 文件，平铺到一个类里
        Context context = new Context(ModelType.FLAT);

        context.setId("default");

//        context.setTargetRuntime("MyBatis3");
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

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        //TODO 自动获取项目根路径 - dao
        javaClientGeneratorConfiguration.setTargetProject("src" + File.separator + "main" + File.separator + "java");
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage(generatorContext.getDaoPackage());
        javaClientGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        //TODO 自动获取项目根路径 - mapper
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject("src" + File.separator + "main" + File.separator + "resources");
        sqlMapGeneratorConfiguration.setTargetPackage(generatorContext.getMapperPackage());
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages","false");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        //TODO 自动获取项目根路径 - entity
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject("src" + File.separator + "main" + File.separator + "java");
        javaModelGeneratorConfiguration.setTargetPackage(generatorContext.getDoPackage());
        javaModelGeneratorConfiguration.addProperty("immutable","false");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","false");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        javaModelGeneratorConfiguration.addProperty("constructorBased","true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);


        TableConfiguration tableConfiguration = new TableConfiguration(context);

        tableConfiguration.setInsertStatementEnabled(true);
        tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setSelectByPrimaryKeyStatementEnabled(true);
        tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(true);


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

        String tableName = generatorContext.getTableName();
        String entityName = generatorContext.getEntityName();

        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(entityName);

        context.addTableConfiguration(tableConfiguration);

        String dateFormat = generatorContext.getDateFormat();
        String author = generatorContext.getAuthor();


        String servicePackage = generatorContext.getServicePackage();
        String serviceImplPackage = generatorContext.getServiceImplPackage();
        String controllerPackage = generatorContext.getControllerPackage();

        if(StringUtils.isNotBlank(servicePackage) && StringUtils.isNotBlank(serviceImplPackage) && StringUtils.isNotBlank(controllerPackage)){
            //生成 service & controller
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

        //注释生成
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

        //jdbc 数据库连接
        gc.setConnectionUrl("jdbc:mysql://192.168.33.10:3306/dddxhh");
        gc.setDriverClass("com.mysql.cj.jdbc.Driver");
        gc.setUsername("dddxhh");
        gc.setPassword("123456");
        gc.setAuthor("DDDXHH");

        gc.setDoPackage("com.opencode.code.entity");
        gc.setDaoPackage("com.opencode.code.dao");
        gc.setMapperPackage("mapper");
        gc.setTableName("ali_xchange_data_reflow");
        gc.setEntityName("AliXchangeDataReflow");

        gc.setServicePackage("com.opencode.code.mybatis.service");
        gc.setServiceImplPackage("com.opencode.code.mybatis.service.impl");
        gc.setControllerPackage("com.opencode.code.mybatis.controller");
        gc.setParamPackage("com.opencode.code.entity.param");
        gc.setVoPackage("com.opencode.code.entity.vo");

        gc.setPrimaryKey("id");
        gc.setObjPrimaryKey("id");

        new GeneratorUtils().generator(gc);
    }

}
