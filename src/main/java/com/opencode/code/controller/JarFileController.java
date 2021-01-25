package com.opencode.code.controller;

import com.opencode.code.classloader.LoaderJarManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 运行时动态加 jar 包哦
 * */
@RestController
@RequestMapping(value = "/jarFile")
public class JarFileController {

    @Autowired
    private LoaderJarManager loaderJarManager;

    @RequestMapping(value = "/execute")
    public Object execute(@RequestParam String name,@RequestParam String method,@RequestParam(required = false) Object[] args) throws Exception {

        try{
            return loaderJarManager.execute(name,method,args);
        }catch(Exception e){
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/register")
    public Object register(@RequestParam String path,@RequestParam String injectBeanName) throws IOException {
//        path = "D:\\my_jar\\TestJarFile.jar";
        try {
            if(StringUtils.isEmpty(injectBeanName)){
                return false;
            }
            Map<String,String> injectMap = new HashMap<>();
            if(injectBeanName.contains(",")){
                String[] split = injectBeanName.split(",");
                for(String spt : split){
                    injectMap.put(spt,spt);
                }
            }else{
                injectMap.put(injectBeanName,injectBeanName);
            }

            loaderJarManager.loaderJar(path,injectMap);
            return true;
        } catch (Exception e) {
            return e.getMessage();

        }
    }

    @RequestMapping(value = "/unregister")
    public Object unregister(@RequestParam String name) {
        try{
            loaderJarManager.unregister(name);
            return true;
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
