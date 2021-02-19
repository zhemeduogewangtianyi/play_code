package com.opencode.code.upload;

import com.opencode.code.upload.center.DeliveryRegisterCenter;
import com.opencode.code.upload.ftp.XchangeFtpClient;

import java.io.File;
import java.io.FileInputStream;

public class TestFtp {

    public static void main(String[] args) throws Exception {

        DeliveryRegisterCenter ftpRegisterCenter = new DeliveryRegisterCenter();
        ftpRegisterCenter.afterPropertiesSet();

        XchangeFtpClient ftpClient = ftpRegisterCenter.getFtpClient(1L);

        File file = new File("C:\\Users\\王添一\\Desktop\\ccc");
        File[] files = file.listFiles();
        int count = 0;
        for(int i = 0 ; i < files.length ; i++){
            if(count > 10){
                count = 0;
            }

            File item = files[i];
            FileInputStream fis = new FileInputStream(item);
            ftpClient.upload(fis,item.getName());
            count++;
        }

    }

}
