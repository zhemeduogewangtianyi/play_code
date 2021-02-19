package com.opencode.code.upload.sftp;

import com.opencode.code.upload.base.pool.BaseDeliveryClientPool;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import com.opencode.code.upload.ftp.XchangeFtpClient;
import com.opencode.code.upload.ftp.XchangeFtpClientFactory;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.*;

/**
 *
 * @author    WTY
 * @date    2021/2/18 15:13
 */
public class XchangeSftpClientPool extends BaseDeliveryClientPool<XchangeSftpClient> {

    @Getter
    @Setter
    private XchangeDeliveryConfig config;

    public XchangeSftpClientPool(XchangeSftpClientFactory sftpFactory) throws Exception {
        this.clientFactory = sftpFactory;
        this.config = sftpFactory.getConfig();
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最大连接
        poolConfig.setMaxTotal(config.getPoolSize());
        //最大空闲连接
        poolConfig.setMaxIdle(config.getPoolSize());

        Integer poolSize = config.getPoolSize();

        Integer configPoolSize = clientFactory.getConfig().getPoolSize();
        poolSize = configPoolSize == null ? poolSize : configPoolSize;
        blockingQueue = new ArrayBlockingQueue<>(poolSize);

        POOL = new ThreadPoolExecutor(poolSize, poolSize * 2, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(poolSize),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
//                            LOGGER.error("Xchange Ftp reset pool error !" , e);
                        }
                    }
                });

        for (int i = 0; i < config.getPoolSize(); i++) {
            // 往池中添加对象
            this.addObject();
        }

    }


    @Override
    public XchangeSftpClient borrowObject() throws Exception {
        XchangeSftpClient client = blockingQueue.take();
        if (ObjectUtils.isEmpty(client)) {
            XchangeSftpClient xfc = clientFactory.create();
            // 放入连接池
            returnObject(xfc);

            return xfc;
            // 验证对象是否有效
        } else if (!clientFactory.validateObject(clientFactory.wrap(client))) {
            // 对无效的对象进行处理
            invalidateObject(client);
            // 创建新的对象
            XchangeSftpClient xfc = clientFactory.create();
            //启动监控
            POOL.execute(xfc);
            // 将新的对象放入连接池
            returnObject(xfc);
            return xfc;
        }
        return client;
    }

    @Override
    public void returnObject(XchangeSftpClient client) {
        try {
            if (client != null && !blockingQueue.offer(client, 3, TimeUnit.SECONDS)) {
                clientFactory.destroyObject(clientFactory.wrap(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invalidateObject(XchangeSftpClient xchangeSFtpClient) {
        blockingQueue.remove(xchangeSFtpClient);
        xchangeSFtpClient.off();
    }

    /**
     * 增加一个新的链接，超时失效
     */
    @Override
    public void addObject() throws Exception {
        XchangeSftpClient client = clientFactory.create();
        //启动监控
        POOL.execute(client);
        // 插入对象到队列
        blockingQueue.offer(client, 3, TimeUnit.SECONDS);
    }

    /**
     * 关闭连接池
     */
    @Override
    public void close() {
        try {
            while (blockingQueue.iterator().hasNext()) {
                XchangeSftpClient client = blockingQueue.take();
                client.off();
                clientFactory.destroyObject(clientFactory.wrap(client));
            }
        } catch (Exception e) {
//            LOGGER.error("close ftp client ftpBlockingQueue failed...", e);
            e.printStackTrace();
        }
    }

}
