package com.opencode.spring.application;

import com.opencode.spring.application.service.StudentService;
import com.opencode.spring.application.service.impl.StudentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Student student(){
        Student stu = new Student();
        stu.setId(1L);
        stu.setAge(11);
        stu.setName("一二");
        return stu;
    }

//    @Bean
//    public StudentService studentService(){
//        return new StudentServiceImpl();
//    }

}
