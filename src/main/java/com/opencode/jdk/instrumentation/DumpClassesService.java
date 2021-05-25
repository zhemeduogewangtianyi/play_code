package com.opencode.jdk.instrumentation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

/**
 * mysql -h127.0.0.1 -P3306 -uroot -proot
 * */
public class DumpClassesService implements ClassFileTransformer {

    private static final List<String> SYSTEM_CLASS_PREFIX = Arrays.asList("java","sum","jdk");

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer)
            throws IllegalClassFormatException
    {

        if(!isSystemClass(className)){
            System.out.println("load class : " + className);
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(className + "dump.class");
                fos.write(classfileBuffer);
                fos.flush();
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classfileBuffer;
    }

    /**
     * 判断是否系统文件
     * */
    private boolean isSystemClass(String className){
        if(null == className || className.isEmpty()){
            return false;
        }
        for(String prefix : SYSTEM_CLASS_PREFIX){
            if(className.startsWith(prefix)){
                return true;
            }
        }
        return false;
    }
}
