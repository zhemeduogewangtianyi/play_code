package com.opencode.code.jdbc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcDmeo {

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from business_name_code");
        List<Map<String,Object>> list = new ArrayList<>();
        while(resultSet.next()){
            Map<String,Object> map = new HashMap<>();
            long id = resultSet.getLong("id");
            Timestamp gmt_create = resultSet.getTimestamp("gmt_create");
            Date gmtCreate = new Date(gmt_create.getTime());
            Timestamp gmt_modified = resultSet.getTimestamp("gmt_modified");
            Date gmtModified = new Date(gmt_modified.getTime());
            String name = resultSet.getString("name");
            String code = resultSet.getString("code");
            String flag = resultSet.getString("flag");
//            map.put("id",id);
//            map.put("gmtCreate",gmtCreate);
//            map.put("gmtModified",gmtModified);
            map.put("code",code);
            map.put("name", name);
            map.put("flag",flag);

            list.add(map);
        }

        Map<Object, List<Map<String, Object>>> meger = list.stream().collect(Collectors.groupingBy(s -> s.get("code")));


        for(Iterator<Map.Entry<Object, List<Map<String, Object>>>> car = meger.entrySet().iterator() ; car.hasNext() ; ){
            Map.Entry<Object, List<Map<String, Object>>> next = car.next();

            List<Map<String, Object>> value = next.getValue();

            List<Map<String,Object>> temp = new ArrayList<>();
            for(Map<String, Object> m : value){
                temp.add(m);
            }
            System.out.println();
        }
    }

}
