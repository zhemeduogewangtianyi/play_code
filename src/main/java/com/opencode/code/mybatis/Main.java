package com.opencode.code.mybatis;


import com.opencode.code.mybatis.generator.context.GeneratorContext;
import com.opencode.code.mybatis.generator.util.GeneratorUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 入口
 */
public class Main {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String,String>() {{
            put("role_publish_record_list","RolePublishRecordList");
            put("role_publish_record_detail","RolePublishRecordDetail");
            put("role_publish_record_error","RolePublishRecordError");
//            put("alarm_threshold_settings_detail","AlarmThresholdSettingsDetail");
//            put("alarm_threshold_settings_values","AlarmThresholdSettingsValues");
        }};
        for(Iterator<Map.Entry<String,String>> car = map.entrySet().iterator() ; car.hasNext() ; ){
            Map.Entry<String, String> next = car.next();
            String tableName = next.getKey();
            String entityName = next.getValue();
            generator(tableName,entityName);
        }


    }

    public static void generator(String tableName,String entityName) {
        GeneratorContext gc = new GeneratorContext();

        //custom jar
//        gc.setMysqlJarPath("mysql-connector-java-8.0.21.jar");

        //jdbc databases Configuration

        gc.setConnectionUrl("jdbc:mysql://localhost:3306/test");

        gc.setDriverClass("com.mysql.cj.jdbc.Driver");

        gc.setUsername("root");
//        gc.setPassword("123456");


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
