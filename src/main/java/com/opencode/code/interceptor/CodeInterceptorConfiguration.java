//package com.opencode.code.interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class CodeInterceptorConfiguration extends WebMvcConfigurerAdapter {
//
//    @Autowired
//    private CodeInterceptor codeInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(this.codeInterceptor)
//                .addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
//
//}
