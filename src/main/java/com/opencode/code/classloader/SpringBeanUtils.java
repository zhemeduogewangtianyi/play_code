package com.opencode.code.classloader;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtils implements ApplicationContextAware, InitializingBean {

    public static SpringBeanUtils springBeanUtils;

    /** 上下文 */
    private ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.configurableApplicationContext = (ConfigurableApplicationContext)applicationContext;
    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)this.configurableApplicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(beanName,beanDefinition);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringBeanUtils.springBeanUtils = this;
    }
}
