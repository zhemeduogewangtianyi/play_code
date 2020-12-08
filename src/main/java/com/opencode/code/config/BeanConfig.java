package com.opencode.code.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.repository.support.Repositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.net.InetAddress;

@Configuration
@MapperScan(basePackages = "com.opencode.code.dao", sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class BeanConfig extends ElasticsearchConfigurationSupport {

//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource druidDataSource = new DriverManagerDataSource();
//        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        druidDataSource.setUrl("jdbc:mysql://192.168.3.10:3306/dddxhh");
//        druidDataSource.setUsername("dddxhh");
//        druidDataSource.setPassword("123456");
//        return druidDataSource;
//    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource druidDataSource = new DriverManagerDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
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

        sqlSessionFactoryBean.setTypeAliasesPackage("com.opencode.code.entity");

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {

        //BSearch
//        String host = "bsearch-gateway.alibaba-inc.com".trim();
//        String port = "80".trim();
//        port = StringUtils.isEmpty(port) ? "80" : port;

//        String host = "127.0.0.1";
        String host = "192.168.3.10";
        String port = "9200";


        HttpHost[] httpHosts = {new HttpHost(host, Integer.parseInt(port))};
        return new RestHighLevelClient(RestClient.builder(httpHosts));

    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }



}
