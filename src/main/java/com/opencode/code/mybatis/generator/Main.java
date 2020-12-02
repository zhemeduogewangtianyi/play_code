package com.opencode.code.mybatis.generator;

import com.opencode.code.mybatis.context.GeneratorContext;
import com.opencode.code.mybatis.generator.util.GeneratorUtils;

public class Main {

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
        GeneratorUtils.generator(gc);
    }

}
