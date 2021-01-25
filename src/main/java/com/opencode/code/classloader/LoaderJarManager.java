package com.opencode.code.classloader;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class LoaderJarManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void loaderJar(String path, Map<String,String> injectBeanNames) throws Exception {

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

            Class<?> aClass = classLoader.loadClass(name.substring(0,name.indexOf(".")));
            System.out.println(aClass);
            if (aClass == null) {
                throw new RuntimeException("不存在的class");
            }

            if(injectBeanNames.containsKey(aClass.getName())){

                //是否是接口
                if (aClass.isInterface()) {
                    throw new RuntimeException("你往 Spring 容器里面放接口？？");
                }

                //是否是抽象类
                if (Modifier.isAbstract(aClass.getModifiers())) {
                    throw new RuntimeException("不支持抽象类CGLIB提升式实例化");
                }

                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
                AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

                beanFactory.registerBeanDefinition(name,beanDefinition);

            }else{

            }
        }
    }

    public boolean unregister(String name) {
        try{
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
            beanFactory.removeBeanDefinition(name);
            return true;
        }catch (Exception e){
           throw new RuntimeException(e);
        }
    }

    public Object execute(String name,String method,Object[] args) throws Exception {

        Object bean = applicationContext.getBean(name);

        System.out.println(bean);

        return bean.getClass().getMethod(method).invoke(bean,args);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
