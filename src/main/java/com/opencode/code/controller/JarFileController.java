package com.opencode.code.controller;

import com.opencode.code.classloader.ProxyLoaderJarClassLoader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RestController
@RequestMapping(value = "/jarFile")
public class JarFileController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        String path = "D:\\my_jar\\TestJarFile.jar";
        new JarFileController().testLoader(path);
    }

    public void testLoader(String path) throws Exception {

        JarFile jarFile = new JarFile(path);

        URL url = new URL("file:" + path);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

        Enumeration<JarEntry> entries = jarFile.entries();

        while(entries.hasMoreElements()){
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            if(!name.endsWith(".class")){
                continue;
            }
            long size = jarEntry.getSize();
            long compressedSize = jarEntry.getCompressedSize();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println(name + " " + size + " " + compressedSize + " " + date);

//            BufferedReader br = new BufferedReader(new InputStreamReader(jarFile.getInputStream(jarEntry), "UTF-8"));
//            String data;
//            while((data = br.readLine()) != null){
//                System.out.println(data);
//            }

            Class<?> aClass = classLoader.loadClass(name.substring(0,name.indexOf(".")));
            System.out.println(aClass);

            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

            beanFactory.registerBeanDefinition(name.substring(0,name.indexOf(".")),beanDefinition);

//            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//
//            applicationContext.register(JarFileController.class);
//
//            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
//            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
//
//            applicationContext.registerBeanDefinition(name,beanDefinition);
//
//            applicationContext.refresh();
//
//            Object bean = applicationContext.getBean(name);
//
//            System.out.println(bean);
//
//            Object getText = bean.getClass().getMethod("getText").invoke(bean);
//
//            System.out.println(getText);
//
//            applicationContext.removeBeanDefinition(name);
//
//            Object bean1 = applicationContext.getBean(name);
//
//            Object getText1 = bean1.getClass().getMethod("getText").invoke(bean1);
//
//            System.out.println(getText1);
//
//            applicationContext.close();

        }
    }

    @RequestMapping(value = "/execute")
    public Object execute(@RequestParam String name,@RequestParam String method,@RequestParam(required = false) Object[] args) throws Exception {

        Object bean = applicationContext.getBean(name);

        System.out.println(bean);

        return bean.getClass().getMethod(method).invoke(bean,args);

    }

    @RequestMapping(value = "/register")
    public Object register(@RequestParam String path) throws IOException {
//        path = "D:\\my_jar\\TestJarFile.jar";
        try {
            testLoader(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/unregister")
    public Object unregister(@RequestParam String name) {
        try{
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
            beanFactory.removeBeanDefinition(name);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
