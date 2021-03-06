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
//        gc.setConnectionUrl("jdbc:mysql://192.168.3.10:3306/dddxhh");
        gc.setConnectionUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC");
        gc.setDriverClass("com.mysql.cj.jdbc.Driver");
//        gc.setUsername("dddxhh");
//        gc.setPassword("123456");

        gc.setUsername("root");
        gc.setPassword("root");

        //user comment configuration
        gc.setAuthor("DDDXHH");

        //name settings
        gc.setTableName("ali_xchange_big_data_map");
        gc.setEntityName("AliXchangeBigDataMap");

        //base mapper package path
        gc.setDoPackage("com.opencode.code.entity");
        gc.setDaoPackage("com.opencode.code.dao");
        gc.setMapperPackage("mapper");

        //extern service & serviceImpl & controller & ViewObject & ParamObject path
        gc.setParamPackage("com.opencode.code.entity.param");
        gc.setVoPackage("com.opencode.code.entity.vo");
        gc.setServicePackage("com.opencode.code.service");
        gc.setServiceImplPackage("com.opencode.code.service.impl");
        gc.setControllerPackage("com.opencode.code.controller");


        //primaryKey
        gc.setPrimaryKey("id");

        //run
        GeneratorUtils.generator(gc);
    }

}
