package com.opencode.code.upload.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import com.opencode.code.upload.base.factory.BaseDeliveryClientFactory;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 *
 * @author    WTY
 * @date    2021/2/18 15:13
 */
public class XchangeSftpClientFactory extends BaseDeliveryClientFactory<XchangeSftpClient> {

    public XchangeSftpClientFactory(XchangeDeliveryConfig config){
        this.config = config;
    }

    @Override
    public XchangeSftpClient create() throws Exception {
        XchangeSftpClient sftpClient = new XchangeSftpClient(config);
        DefaultPooledObject<XchangeSftpClient> defaultPooledObject = new DefaultPooledObject<>(sftpClient);
        return defaultPooledObject.getObject();
    }

    @Override
    public PooledObject<XchangeSftpClient> wrap(XchangeSftpClient xchangeSFtpClient) {
        return new DefaultPooledObject<>(xchangeSFtpClient);
    }


    @Override
    public void destroyObject(PooledObject<XchangeSftpClient> pooledObject) throws Exception {
        XchangeSftpClient sFtpClient = pooledObject.getObject();
        Channel sftpChanel = sFtpClient.getChannel();
        Session session = sftpChanel.getSession();
        session.disconnect();
        sftpChanel.disconnect();

    }

    @Override
    public boolean validateObject(PooledObject<XchangeSftpClient> sftpPooled) {
        XchangeSftpClient sftpClient = sftpPooled.getObject();
        XchangeDeliveryConfig sftpConfig = sftpClient.getConfig();
        Long clientCreateTime = sftpConfig.getVersion();
        Long contextCreateTime = this.getConfig().getVersion();
        if(!clientCreateTime.equals(contextCreateTime)){
            return false;
        }
        Channel sftpChanel = sftpClient.getChannel();
        return sftpChanel.isConnected();
    }

}
