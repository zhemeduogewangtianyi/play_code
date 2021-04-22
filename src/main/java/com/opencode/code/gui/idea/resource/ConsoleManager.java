package com.opencode.code.gui.idea.resource;

import java.io.*;

public class ConsoleManager {

    public static synchronized String console(Process exec){

        StringBuilder sb = new StringBuilder();

        try {
            exec.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = exec.getInputStream();

        InputStream errorStream = exec.getErrorStream();

        SequenceInputStream sis = new SequenceInputStream(is,errorStream);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
            isr = new InputStreamReader(sis,"GBK");
            br = new BufferedReader(isr);
            String okData;
            while((okData = br.readLine()) != null){
                sb.append(okData).append("\r\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br != null){
                    br.close();
                }
                if(isr != null){
                    isr.close();
                }
                sis.close();
                is.close();
                errorStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
