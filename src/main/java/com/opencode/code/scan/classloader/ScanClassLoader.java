package com.opencode.code.scan.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ScanClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes;
        Class<?> clazz;

        try {
            //获取.class 文件的二进制字节
            bytes = getClassByte(name);
            //将二进制字节转化为Class对象
            clazz = defineClass(name,bytes,0,bytes.length);
            return clazz;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    byte[] getClassByte(String fileName) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try{
            File file = new File(fileName);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            int len;
            byte[] data = new byte[1024];
            while((len = fis.read(data)) != -1){
                bos.write(data , 0 , len);
            }
            return bos.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("not found !");
    }

}
