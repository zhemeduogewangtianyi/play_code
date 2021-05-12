package com.opencode.code.proxy.annotation;

import com.opencode.code.proxy.register.CarrotImportBeanDefinitionRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Documented
@Import(CarrotImportBeanDefinitionRegister.class)
public @interface CarrotScan {


}
