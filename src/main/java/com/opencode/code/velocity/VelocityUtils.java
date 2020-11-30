package com.opencode.code.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** 模板 */
public class VelocityUtils {

    private static final VelocityEngine velocityEngine;

    static{
        Properties properties = new Properties();
        properties.put("input.encoding","utf-8");
        properties.put("output.encoding","utf-8");
        properties.put("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.put("resource.loader","class");
        velocityEngine = new VelocityEngine(properties);
    }

    public static String generator(String template, Map<String,Object> datas){
        Context context = new VelocityContext(datas);
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context,writer,"",template);
        return writer.toString();
    }

    public static void main(String[] args){
        Map<String,Object> map = new HashMap<>();
        map.put("name","低调的小黑孩");
        map.put("age","16");
        map.put("gender","你猜");

        String template = "\r **** 这是一个示例的模板 **** \r\n **** name:$!{name} - age:$!{age} - gender:$!{gender} **** \r\n";

        String generator = VelocityUtils.generator(template, map);

        System.out.println(generator);

    }

}
