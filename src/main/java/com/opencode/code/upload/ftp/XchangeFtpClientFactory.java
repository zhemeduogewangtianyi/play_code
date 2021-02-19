package com.opencode.code.upload.ftp;

import com.opencode.code.upload.base.factory.BaseDeliveryClientFactory;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class XchangeFtpClientFactory extends BaseDeliveryClientFactory<XchangeFtpClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XchangeFtpClientFactory.class);

    private static final Logger FTP_LOGGER = LoggerFactory.getLogger(XchangeFtpClientFactory.class);

    public XchangeFtpClientFactory(XchangeDeliveryConfig config) {
        this.config = config;
    }

    @Override
    public XchangeFtpClient create() {
        XchangeFtpClient ftpClient = new XchangeFtpClient(config);
        return ftpClient.getClient();
    }

    /**
     * 用PooledObject封装对象放入池中
     */
    @Override
    public PooledObject<XchangeFtpClient> wrap(XchangeFtpClient ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }

    /**
     * 销毁FtpClient对象
     */
    @Override
    public void destroyObject(PooledObject<XchangeFtpClient> ftpPooled) {
        if (ftpPooled == null) {
            return;
        }

        XchangeFtpClient ftpClient = ftpPooled.getObject();

        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException io) {
            FTP_LOGGER.error("ftp client logout failed...", io);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException io) {
                FTP_LOGGER.error("close ftp client failed...", io);
            }
        }
    }

    /**
     * 验证FtpClient对象
     */
    @Override
    public boolean validateObject(PooledObject<XchangeFtpClient> ftpPooled) {
        try {
            XchangeFtpClient ftpClient = ftpPooled.getObject();
            Long clientCreateTime = ftpClient.getConfig().getVersion();
            Long contextCreateTime = this.getConfig().getVersion();
            if(!clientCreateTime.equals(contextCreateTime)){
                return false;
            }
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            FTP_LOGGER.warn("ftp client connection is closed");
        }
        return false;
    }

}
