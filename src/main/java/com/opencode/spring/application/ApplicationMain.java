package com.opencode.spring.application;

import com.opencode.spring.application.service.StudentService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(Config.class);

//        BeanDefinitionBuilder studentServiceBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(StudentService.class);
//        AbstractBeanDefinition studentServiceBeanDefinition = studentServiceBeanDefinition.getBeanDefinition();
//        context.registerBeanDefinition(null , configBeanDefinition);

        context.refresh();

        Config bean = context.getBean(Config.class);
        StudentService bean1 = context.getBean(StudentService.class);

        System.out.println(111);

        context.destroy();

    }

}
