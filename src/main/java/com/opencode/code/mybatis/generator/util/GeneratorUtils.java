package com.opencode.code.mybatis.generator.util;

import com.opencode.code.mybatis.generator.config.GeneratorConfiguration;
import com.opencode.code.mybatis.generator.context.GeneratorContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;


public class GeneratorUtils {

    //generator
    public static void generator(GeneratorContext gc) {
        List<String> warnings = new ArrayList<>();
        try {
            Configuration config = new GeneratorConfiguration().configuration(gc);
            //是否覆盖
            DefaultShellCallback dsc = new DefaultShellCallback(true);
            MyBatisGenerator mg = new MyBatisGenerator(config, dsc, warnings);
            mg.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
