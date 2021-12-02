package com.opencode.code.mybatis;


import com.opencode.code.mybatis.generator.context.GeneratorContext;
import com.opencode.code.mybatis.generator.util.GeneratorUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 入口
 * */
public class Main {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String,String>() {{
            put("alarm_threshold_settings_group","AlarmThresholdSettingsGroup");
            put("alarm_threshold_settings_item","AlarmThresholdSettingsItem");
            put("alarm_threshold_settings_detail","AlarmThresholdSettingsDetail");
            put("alarm_threshold_settings_values","AlarmThresholdSettingsValues");
        }};
        for(Iterator<Map.Entry<String,String>> car = map.entrySet().iterator() ; car.hasNext() ; ){
            Map.Entry<String, String> next = car.next();
            String tableName = next.getKey();
            String entityName = next.getValue();
            generator(tableName,entityName);
        }


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

    public static void generator(String tableName,String entityName) {
        GeneratorContext gc = new GeneratorContext();

        //custom jar
//        gc.setMysqlJarPath("mysql-connector-java-8.0.21.jar");

        //jdbc databases Configuration

        gc.setConnectionUrl("jdbc:mysql://localhost:3306/test");
//        gc.setConnectionUrl("jdbc:mysql://test.database3500.scsite.net:3500/danube_topcars?useUnicode=true&amp&characterEncoding=UTF-8");
        gc.setDriverClass("com.mysql.cj.jdbc.Driver");

        gc.setUsername("root");
//        gc.setPassword("123456");

//        gc.setUsername("souche_rw");
//        gc.setPassword("Ewm3prj6WRcwbv4x");

        //user comment configuration
        gc.setAuthor("DDDXHH");

        //name settings
        gc.setTableName(tableName);
        gc.setEntityName(entityName);

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
