package com.opencode.code.upload.base.pool;

import com.opencode.code.upload.base.factory.BaseDeliveryClientFactory;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import org.apache.commons.pool2.BaseObjectPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class BaseDeliveryClientPool<T> extends BaseObjectPool<T> {

    //线程池
    protected ThreadPoolExecutor POOL;

    //默认线程池大小
    protected static final int DEFAULT_POOL_SIZE = 8;

    //连接阻塞队列
    protected BlockingQueue<T> blockingQueue;

    //连接工厂
    protected BaseDeliveryClientFactory<T> clientFactory;

    public void setFtpConfig(XchangeDeliveryConfig config) {
        this.clientFactory.setConfig(config);
    }

    public XchangeDeliveryConfig getDeliveryConfig() {
        return this.clientFactory.getConfig();
    }

}
