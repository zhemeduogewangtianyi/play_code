package com.opencode.code.scan;

import com.opencode.code.scan.annotation.ScanProcessor;
import com.opencode.code.scan.defination.ProcessorBeanDefinition;
import com.opencode.code.scan.register.ProcessorRegister;
import com.opencode.code.scan.service.Processor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.security.ProtectionDomain;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {
        System.out.println("111");
        ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
        String filePath = protectionDomain.getCodeSource().getLocation().getFile();

        File file = new File(filePath);
        scan(file,file);

//        findFile(filePath,classLoader);
        Class<Processor> processorClass = Processor.class;
        Processor bean = ProcessorRegister.getBean(processorClass);
        System.out.println(bean.process(null));
    }

    public static void scan(File file,File sourceFile) {
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                scan(f,sourceFile);
            }else{
                String path = f.getPath();
                if(path.endsWith(".class")){
                    String sourcePath = sourceFile.getPath();
                    String nowPath = f.getPath();
                    String s = nowPath.replace(sourcePath, "");
                    String s1 = s.replaceAll(Matcher.quoteReplacement(File.separator), ".").substring(1);
                    String classPath = s1.substring(0, s1.lastIndexOf(".class"));
                    Class<?> aClass = null;
                    try {
                        aClass = Class.forName(classPath);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                    if(aClass != null){

                        ScanProcessor annotation = aClass.getAnnotation(ScanProcessor.class);
                        if(annotation != null){

                            String beanName = annotation.name();
                            if(StringUtils.isEmpty(beanName)){
                                beanName = aClass.getSimpleName();
                            }

                            ProcessorBeanDefinition beanDefinition = new ProcessorBeanDefinition();
                            beanDefinition.setClz(aClass);
                            beanDefinition.setName(beanName);

                            ProcessorRegister.register(beanName,beanDefinition);
                        }

                    }

                }

            }
        }
    }



}
