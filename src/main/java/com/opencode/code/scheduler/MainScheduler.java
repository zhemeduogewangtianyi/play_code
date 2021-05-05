package com.opencode.code.scheduler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@RestController
@Configuration
@EnableScheduling
public class MainScheduler implements ApplicationContextAware, InitializingBean {

    @RequestMapping("/shutdown")
    public Object shutdownScheduled() throws IllegalAccessException, NoSuchFieldException {
        this.applicationContext.getBean(MainScheduler.class).shutdown();
        return "success";
    }

    @RequestMapping("/start")
    public Object startScheduled(){
        this.applicationContext.getBean(MainScheduler.class).start();
        return "success";
    }

    private ApplicationContext applicationContext ;

    private TaskScheduler taskScheduler;

    @Scheduled(fixedDelay = 5000L,initialDelay = 3000)
    public void one(){
        System.out.println("1111111111");
    }

    @Scheduled(fixedDelay = 5000L,initialDelay = 3000)
    public void two(){
        System.out.println("2222222222");
    }

    @Bean
    public TaskScheduler taskScheduler(){
        TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        return taskScheduler;
    }

    public void shutdown() throws IllegalAccessException, NoSuchFieldException {
        Field executorFiled = taskScheduler.getClass().getSuperclass().getDeclaredField("executor");
        executorFiled.setAccessible(true);
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor)executorFiled.get(taskScheduler);

        BlockingQueue<Runnable> queue = executor.getQueue();
        for(Runnable r : queue){
            executor.remove(r);
            System.out.println(r);
        }
    }

    public void start(){
        AnnotationConfigServletWebServerApplicationContext annotationConfigApplicationContext = (AnnotationConfigServletWebServerApplicationContext)this.applicationContext;
        annotationConfigApplicationContext.publishEvent(new ContextRefreshedEvent(annotationConfigApplicationContext));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskScheduler = applicationContext.getAutowireCapableBeanFactory().getBean(TaskScheduler.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
