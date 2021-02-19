package com.opencode.code.controller;

import com.opencode.code.upload.config.XchangeDeliveryConfig;
import com.opencode.code.upload.ftp.XchangeFtpClient;
import com.opencode.code.upload.center.DeliveryRegisterCenter;
import com.opencode.code.upload.sftp.XchangeSftpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

@RequestMapping(value = "/ftp")
@RestController
public class FtpController {

    @Autowired
    private DeliveryRegisterCenter registerCenter;

    @RequestMapping(value = "/add")
    public Object add(@RequestBody XchangeDeliveryConfig config){
        try {
            return registerCenter.add(config);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/delete")
    public Object add(@RequestParam Long id){
        try {
            return registerCenter.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/update")
    public Object update(@RequestBody XchangeDeliveryConfig config){
        try {
            return registerCenter.update(config);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/info")
    public Object info(){
        try {
            return registerCenter.info();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/upload")
    public Object update(@RequestParam Long id){
        try {

            File file = new File("C:\\Users\\王添一\\Desktop\\ccc");
            File[] files = file.listFiles();
            int count = 0;
            for(int i = 0 ; i < files.length ; i++){
                if(count > 10){
                    count = 0;
                }
                XchangeFtpClient ftpClient = registerCenter.getFtpClient(id);
                File item = files[i];
                FileInputStream fis = new FileInputStream(item);
                ftpClient.upload(fis,item.getName());
                registerCenter.returnFTPConnection(id,ftpClient);
                count++;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/uploadsftp")
    public Object uploadsftp(@RequestParam Long id){
        try {

            File file = new File("C:\\Users\\王添一\\Desktop\\ccc");
            File[] files = file.listFiles();
            int count = 0;
            for(int i = 0 ; i < files.length ; i++){
                if(count > 10){
                    count = 0;
                }
                XchangeSftpClient sftpClient = registerCenter.getSftpClient(id);
                File item = files[i];
                FileInputStream fis = new FileInputStream(item);
                sftpClient.upload(fis,item.getName());
                registerCenter.returnSFTPConnection(id,sftpClient);
                count++;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
