package com.opencode.code.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorUtils {

    public static void generator() {
        List<String> warnings = new ArrayList<>();
        try {
            //导入配置表mybatis-generator.xml
            File configFile = new File("D:\\project\\code\\src\\main\\resources\\mybatis-generator.xml");
            //      解析
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            //      是否覆盖
            DefaultShellCallback dsc = new DefaultShellCallback(true);
            MyBatisGenerator mg = new MyBatisGenerator(config, dsc, warnings);
            System.out.println(mg);
//            mg.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        MyBatisGenerator generator = new MyBatisGenerator();
        generator();
    }

}
