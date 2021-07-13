package com.opencode.csearch;

import com.alibaba.fastjson.JSON;
import com.carrot.sec.interfaces.Operations;
import com.carrot.sec.operation.Operation;
import com.carrot.sec.test.entity.Student;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CSearchClient {

    public static void main(String[] args) throws Throwable {

        long start = System.currentTimeMillis();

        Operations operations = new Operation("jdbc:carrot-search://127.0.0.1:9527/temp","root", "root");

        Student insert = new Student();
        insert.setId(1L);
        insert.setName("周多余 1 + 1");
        insert.setAge((int) (System.currentTimeMillis() / 1000000));
        insert.setDesc("周童童 是一个好学生，太好了，真的是太好了！￥%……");
        insert.setUrl("http://www.baidu.com");
        insert.setBirthDay(new Date());

        boolean ui = operations.create(insert);
        System.out.println("新增：" + ui);

        Student stuQ1 = new Student();
        stuQ1.setId(1L);
        List<Map<String, Object>> queryRes1 = operations.select(stuQ1, 0, 100);
        System.out.println("新增后查询结果：" + JSON.toJSONString(queryRes1));


        Student update = new Student();
        update.setId(1L);
        update.setName("周童童");
        update.setAge(10);
        update.setDesc("周童童 是一个好学生，太好了，真的是太好了！￥%……");
        update.setUrl("http://www.google.com");
        update.setBirthDay(new Date());
        boolean ub = operations.update(update);
        System.out.println("修改：" + ub);

        Student stuQ2 = new Student();
        stuQ2.setId(1L);
        List<Map<String, Object>> queryRes2 = operations.select(stuQ2, 0, 100);
        System.out.println("修改后后查询结果：" + JSON.toJSONString(queryRes2));

        Student deleteStu = new Student();
        deleteStu.setId(1L);
        boolean delete = operations.delete(deleteStu);
        System.out.println("删除：" + delete);

        Student stuQ3 = new Student();
        stuQ3.setId(1L);
        List<Map<String, Object>> queryRes3 = operations.select(stuQ3, 0, 100);
        System.out.println("删除后后查询结果：" + JSON.toJSONString(queryRes3));


        Student stu = new Student();
        List<Map<String, Object>> select = operations.select(stu, 0, 1000000000);
        System.out.println("查询全部结果：" + select.size());


        System.out.println("耗时" + (System.currentTimeMillis() - start) + " ms");

    }

}
