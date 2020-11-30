package com.opencode.code.mybatis.generator;

import com.alibaba.fastjson.JSON;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeneratorUtils implements ApplicationContextAware, InitializingBean {

    private DataSource dataSource;

    public void generator() {
        List<String> warnings = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            //导入配置表mybatis-generator.xml
            File configFile = new File("D:\\project\\code\\src\\main\\resources\\mybatis-generator.xml");
            //解析
            ConfigurationParser cp = new ConfigurationParser(warnings);
            System.out.println(JSON.toJSONString(cp));
            Configuration config = cp.parseConfiguration(configFile);
            System.out.println(JSON.toJSONString(config));
            //是否覆盖
            DefaultShellCallback dsc = new DefaultShellCallback(true);
            System.out.println(JSON.toJSONString(dsc));
            MyBatisGenerator mg = new MyBatisGenerator(config, dsc, warnings);
            System.out.println(JSON.toJSONString(mg));
//            mg.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        MyBatisGenerator generator = new MyBatisGenerator();
//        generator();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.dataSource = applicationContext.getAutowireCapableBeanFactory().getBean(DataSource.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        generator();
    }
}
