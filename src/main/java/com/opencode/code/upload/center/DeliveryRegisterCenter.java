package com.opencode.code.upload.center;

import com.opencode.code.upload.base.pool.BaseDeliveryClientPool;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import com.opencode.code.upload.ftp.XchangeFtpClient;
import com.opencode.code.upload.ftp.XchangeFtpClientFactory;
import com.opencode.code.upload.ftp.XchangeFtpClientPool;
import com.opencode.code.upload.sftp.XchangeSftpClient;
import com.opencode.code.upload.sftp.XchangeSftpClientPool;
import com.opencode.code.upload.sftp.XchangeSftpClientFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FTP连接注册中心
 *
 * 使用方式
 *
 * @Autowired
 * private DeliveryRegisterCenter center;
 *
 * XchangeFtpClient client = center.getFtpClient( 投递ID )
 *
 * client.upload(流 , 文件名)
 *
 * 一定记住返回连接，不然会导致阻塞 ！！！
 * center.returnFTPConnection(client); 或者 center.returnSFTPConnection(client);
 *
 * @author    WTY
 * @date    2021/2/4 15:55
 */
@Component
public class DeliveryRegisterCenter implements InitializingBean {

    private static final Map<Long, BaseDeliveryClientPool<XchangeFtpClient>> FTP_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, BaseDeliveryClientPool<XchangeSftpClient>> SFTP_MAP = new ConcurrentHashMap<>();

    String host = "11.164.25.64";
    String username = "ftpuser";
    String password = "ftpuser";
    Integer port = 21;

    public XchangeFtpClient getFtpClient(Long key){

        if(FTP_MAP.containsKey(key)){
            try {
                return FTP_MAP.get(key).borrowObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public XchangeSftpClient getSftpClient(Long key){

        if(SFTP_MAP.containsKey(key)){
            try {
                return SFTP_MAP.get(key).borrowObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Map<Long,Object>> info(){

        List<Map<Long,Object>> result = new ArrayList<>();

        for(Iterator<Map.Entry<Long, BaseDeliveryClientPool<XchangeFtpClient>>> car = FTP_MAP.entrySet().iterator(); car.hasNext() ; ){
            Map<Long,Object> map = new LinkedHashMap<>();
            Map.Entry<Long, BaseDeliveryClientPool<XchangeFtpClient>> next = car.next();
            Long key = next.getKey();
            BaseDeliveryClientPool<XchangeFtpClient> value = next.getValue();
            XchangeDeliveryConfig ftpConfig = value.getDeliveryConfig();
            map.put(key,ftpConfig);
            result.add(map);
        }
        return result;
    }

    public boolean add(XchangeDeliveryConfig config) throws Exception {
        config.setVersion(config.getVersion() == null ? 0 : config.getVersion());
        Long ftpId = config.getDeliveryId();

        Integer protocol = config.getProtocol();
        switch (protocol){
            case 0:
                if(FTP_MAP.containsKey(ftpId)){
                    return false;
                }
                XchangeFtpClientFactory ftpClientFactory = new XchangeFtpClientFactory(config);
                XchangeFtpClientPool ftpClientPool = new XchangeFtpClientPool(ftpClientFactory);
                FTP_MAP.put(ftpId,ftpClientPool);
                return true;
            case 1:
                if(SFTP_MAP.containsKey(ftpId)){
                    return false;
                }
                XchangeSftpClientFactory sftpClientFactory = new XchangeSftpClientFactory(config);
                XchangeSftpClientPool sftpClientPool = new XchangeSftpClientPool(sftpClientFactory);
                SFTP_MAP.put(ftpId,sftpClientPool);
                return true;
        }

        return true;
    }

    public boolean delete(Long id) {
        int flag = -1;
        if(FTP_MAP.containsKey(id)){
            flag = 0;
        } else if(SFTP_MAP.containsKey(id)){
            flag = 1;
        }
        if(flag < 0){
            return false;
        }
        if(flag == 0){
            BaseDeliveryClientPool<XchangeFtpClient> clientPool = FTP_MAP.get(id);
            clientPool.close();
            FTP_MAP.remove(id);
        }else {
            BaseDeliveryClientPool<XchangeSftpClient> clientPool = SFTP_MAP.get(id);
            clientPool.close();
            SFTP_MAP.remove(id);
        }

        return true;
    }

    public boolean update(XchangeDeliveryConfig config) {
        int flag = -1;
        Long ftpId = config.getDeliveryId();
        if(FTP_MAP.containsKey(ftpId)){
            flag = 0;
        } else if(SFTP_MAP.containsKey(ftpId)){
            flag = 1;
        }
        if(flag < 0){
            return false;
        }

        if(flag == 0){
            BaseDeliveryClientPool<XchangeFtpClient> oldFtpClientPool = FTP_MAP.get(ftpId);
            if(oldFtpClientPool == null){
                return false;
            }
            XchangeDeliveryConfig oldConfig = oldFtpClientPool.getDeliveryConfig();
            config.setVersion(oldConfig.getVersion() + 1);
            oldFtpClientPool.setFtpConfig(config);
            return true;
        }else {
            BaseDeliveryClientPool<XchangeSftpClient> oldFtpClientPool = SFTP_MAP.get(ftpId);
            if(oldFtpClientPool == null){
                return false;
            }
            XchangeDeliveryConfig oldConfig = oldFtpClientPool.getDeliveryConfig();
            config.setVersion(oldConfig.getVersion() + 1);
            oldFtpClientPool.setFtpConfig(config);
            return true;
        }

    }

    public void returnFTPConnection(Long id,XchangeFtpClient client) {

        XchangeFtpClientPool ftpClientPool = (XchangeFtpClientPool)FTP_MAP.get(id);
        if(ftpClientPool != null){
            ftpClientPool.returnObject(client);
        }
    }

    public void returnSFTPConnection(Long id,XchangeSftpClient client) {

        XchangeSftpClientPool sftpClientPool = (XchangeSftpClientPool) SFTP_MAP.get(id);
        if(sftpClientPool != null){
            sftpClientPool.returnObject(client);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        XchangeDeliveryConfig config = new XchangeDeliveryConfig();
        config.setDeliveryId(1L);
        config.setHost(host);
        config.setUsername(username);
        config.setPassword(password);
        config.setPort(port);

        config.setChangeDir("cccc");
        config.setPoolSize(10);
        config.setCheckFrequency(1L);
        config.setConnectTimeout(5000);
        config.setVersion(0L);
        XchangeFtpClientFactory factory = new XchangeFtpClientFactory(config);
        XchangeFtpClientPool ftpClientPool = new XchangeFtpClientPool(factory);
        FTP_MAP.put(1L,ftpClientPool);


        XchangeDeliveryConfig sconfig = new XchangeDeliveryConfig();
        sconfig.setDeliveryId(2L);
        sconfig.setHost(host);
        sconfig.setUsername("sftpuser");
        sconfig.setPassword("sftpuser");
        sconfig.setPort(22);

        sconfig.setChangeDir("dddd");
        sconfig.setPoolSize(10);
        sconfig.setCheckFrequency(1L);
        sconfig.setConnectTimeout(5000);
        sconfig.setVersion(0L);
        XchangeSftpClientFactory sfactory = new XchangeSftpClientFactory(sconfig);
        XchangeSftpClientPool sftpClientPool = new XchangeSftpClientPool(sfactory);
        SFTP_MAP.put(2L,sftpClientPool);

    }
}
