package com.opencode.code.groovy;


import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

/**
 * groovy 动态执行
 * */
public class GroovyUtils {

    public static Object evalScript(String script,String methodName,Object[] args) throws IllegalAccessException, InstantiationException {
        Class cls = new GroovyClassLoader().parseClass(script);
        GroovyObject gObj = (GroovyObject)cls.newInstance();
        return gObj.invokeMethod(methodName, args);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        String script = "class Test {\n" +
                "\n" +
                "    Object solution(String name,String age){\n" +
                "        List<Map<String,Object>> list = []\n" +
                "        Map<String,Object> map = [:]\n" +
                "        map.put(\"name\",name)\n" +
                "        map.put(\"age\",age)\n" +
                "        list.add(map)\n" +
                "        return list\n" +
                "    }\n" +
                "\n" +
                "}";
        Object[] arg = {"低调的小黑孩","18"};
        Object solution = GroovyUtils.evalScript(script, "solution", arg);
        System.out.println(solution);

    }

}
