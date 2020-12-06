package com.opencode.code.mybatis;


import com.opencode.code.mybatis.generator.context.GeneratorContext;
import com.opencode.code.mybatis.generator.util.GeneratorUtils;

/**
 * 入口
 * */
public class Main {

    public static void main(String[] args) {

        GeneratorContext gc = new GeneratorContext();

        //custom jar
//        gc.setMysqlJarPath("mysql-connector-java-8.0.21.jar");

        //jdbc databases Configuration
        gc.setConnectionUrl("jdbc:mysql://192.168.3.10:3306/dddxhh");
        gc.setDriverClass("com.mysql.cj.jdbc.Driver");
        gc.setUsername("dddxhh");
        gc.setPassword("123456");

        //user comment configuration
        gc.setAuthor("DDDXHH");

        //name settings
        gc.setTableName("test");
        gc.setEntityName("Test");

        //base mapper package path
        gc.setDoPackage("com.opencode.code.test.entity");
        gc.setDaoPackage("com.opencode.code.test.dao");
        gc.setMapperPackage("mapper");

        //extern service & serviceImpl & controller & ViewObject & ParamObject path
        gc.setParamPackage("com.opencode.code.test.entity.param");
        gc.setVoPackage("com.opencode.code.test.entity.vo");
        gc.setServicePackage("com.opencode.code.test.service");
        gc.setServiceImplPackage("com.opencode.code.test.service.impl");
        gc.setControllerPackage("com.opencode.code.test.controller");


        //primaryKey
        gc.setPrimaryKey("id");

        //run
        GeneratorUtils.generator(gc);
    }

}
