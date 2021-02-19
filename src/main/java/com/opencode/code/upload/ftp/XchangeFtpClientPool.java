package com.opencode.code.upload.ftp;

import com.opencode.code.upload.base.pool.BaseDeliveryClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.concurrent.*;

public class XchangeFtpClientPool extends BaseDeliveryClientPool<XchangeFtpClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XchangeFtpClientPool.class);

    /**
     * 初始化连接池，需要注入一个工厂来提供FTPClient实例
     *
     * @param ftpClientFactory ftp工厂
     * @throws Exception
     */
    public XchangeFtpClientPool(XchangeFtpClientFactory ftpClientFactory) throws Exception {
        this(DEFAULT_POOL_SIZE, ftpClientFactory);
    }

    public XchangeFtpClientPool(int poolSize, XchangeFtpClientFactory factory) throws Exception {
        this.clientFactory = factory;

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
                            LOGGER.error("Xchange Ftp reset pool error !" , e);
                        }
                    }
                });

        initPool(poolSize);
    }

    /**
     * 初始化连接池，需要注入一个工厂来提供FTPClient实例
     *
     * @param maxPoolSize 最大连接数
     * @throws Exception
     */
    private void initPool(int maxPoolSize) throws Exception {
        for (int i = 0; i < maxPoolSize; i++) {
            // 往池中添加对象
            addObject();
        }
    }

    /**
     * 从连接池中获取对象
     */
    @Override
    public XchangeFtpClient borrowObject() throws Exception {
        XchangeFtpClient client = blockingQueue.take();
        if (ObjectUtils.isEmpty(client)) {
            XchangeFtpClient xfc = clientFactory.create();
            // 放入连接池
            returnObject(xfc);

            return xfc;
            // 验证对象是否有效
        } else if (!clientFactory.validateObject(clientFactory.wrap(client))) {
            // 对无效的对象进行处理
            invalidateObject(client);
            // 创建新的对象
            XchangeFtpClient xfc = clientFactory.create();
            //启动监控
            POOL.execute(xfc);
            // 将新的对象放入连接池
            returnObject(xfc);
            return xfc;
        }
        return client;
    }

    /**
     * 返还对象到连接池中
     */
    @Override
    public void returnObject(XchangeFtpClient client) {
        try {
            if (client != null && !blockingQueue.offer(client, 3, TimeUnit.SECONDS)) {
                ((XchangeFtpClientFactory)clientFactory).destroyObject(clientFactory.wrap(client));
            }
        } catch (InterruptedException e) {
            LOGGER.error("return ftp client interrupted ...", e);
        }
    }

    /**
     * 移除无效的对象
     */
    @Override
    public void invalidateObject(XchangeFtpClient client) {
        try {
            blockingQueue.remove(client);
            client.off();
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个新的链接，超时失效
     */
    @Override
    public void addObject() throws Exception {
        XchangeFtpClient client = clientFactory.create();
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
                XchangeFtpClient client = blockingQueue.take();
                client.off();
                clientFactory.destroyObject(clientFactory.wrap(client));
            }
        } catch (Exception e) {
            LOGGER.error("close ftp client ftpBlockingQueue failed...", e);
        }
    }

}
