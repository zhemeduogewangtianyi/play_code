package com.opencode.code.config;

import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class BeanConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource druidDataSource = new DriverManagerDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://192.168.33.10:3306/dddxhh");
        druidDataSource.setUsername("dddxhh");
        druidDataSource.setPassword("123456");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();

        //日志
//        configuration.setLogImpl(StdOutImpl.class);
        configuration.setLogImpl(NoLoggingImpl.class);
        //驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);
        //属性 是 null 也 set
        configuration.setCallSettersOnNulls(true);

        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(dataSource);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 设置mapper文件扫描路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));

        return sqlSessionFactoryBean;
    }

}
