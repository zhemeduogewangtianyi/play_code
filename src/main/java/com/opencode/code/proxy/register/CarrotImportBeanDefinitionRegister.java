package com.opencode.code.proxy.register;

import com.opencode.code.proxy.factory.CarrotFactoryBean;
import com.opencode.code.proxy.interfaces.UserService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

@Component
public class CarrotImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CarrotFactoryBean.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserService.class);
        registry.registerBeanDefinition("carrot$" + UserService.class.getSimpleName(),beanDefinition);
    }
}
