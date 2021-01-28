package com.opencode.code.sftp.low.manager;

import com.opencode.code.sftp.low.config.SftpConfig;
import com.opencode.code.sftp.low.sftptask.SftpClientTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SftpClientRegisterCenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpClientRegisterCenter.class);

    private static final Map<Long,SftpClientQueue> SFTP_MAP = new HashMap<>();

    public static boolean upload(Long id, String fileName, InputStream is) throws Exception{
        SftpClientQueue sftpClientQueue = SFTP_MAP.get(id);
        return sftpClientQueue.upload(fileName,is);
    }

    public static synchronized boolean addClient(SftpConfig sftpConfig){

        Long id = sftpConfig.getId();
        try{
            if(SFTP_MAP.containsKey(id)){
                SftpClientQueue sftpClientQueue = SFTP_MAP.get(id);
                if(sftpClientQueue.getQueueSize() < sftpClientQueue.getPoolSize()){
                    sftpClientQueue.addOneClient(sftpConfig);
                }
                return true;
            }else{
                SftpClientQueue sftpClientQueue = new SftpClientQueue(sftpConfig);
                if(sftpClientQueue.getQueueSize() < sftpClientQueue.getPoolSize()){
                    sftpClientQueue.addOneClient(sftpConfig);
                    SFTP_MAP.put(id,sftpClientQueue);
                    return true;
                }
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static synchronized boolean updateConfig(SftpConfig sftpConfig){

        Long id = sftpConfig.getId();
        try{
            if(SFTP_MAP.containsKey(id)){

                SftpClientQueue sftpClientQueue = SFTP_MAP.get(id);
                sftpClientQueue.updateSftpConfig(sftpConfig);
                return true;
            }else{
                LOGGER.error("not found {} !" ,id );
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static synchronized boolean removeOneClient(Long id, SftpClientTask sftpClientThread){
        if(SFTP_MAP.containsKey(id)){

            SftpClientQueue sftpClientQueue = SFTP_MAP.get(id);
            return sftpClientQueue.removeOneClient(sftpClientThread);

        }
        return false;
    }

}
