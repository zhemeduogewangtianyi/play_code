package com.opencode.code.gui.idea.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    public static String fileLoad(String dir,String fileName){
        if(dir == null || fileName == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        File file = new File(dir + fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] data = new byte[1024];
            int len;
            while((len = fis.read(data)) != -1){
                sb.append(new String(data,0,len,"UTF-8"));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static boolean fileSave(String dir,String fileName,String content){
        File file = new File(dir + fileName);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
