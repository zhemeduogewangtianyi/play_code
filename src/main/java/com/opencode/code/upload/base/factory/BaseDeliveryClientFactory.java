package com.opencode.code.upload.base.factory;

import com.opencode.code.upload.config.XchangeDeliveryConfig;
import org.apache.commons.pool2.BasePooledObjectFactory;

public abstract class BaseDeliveryClientFactory<T> extends BasePooledObjectFactory<T> {

    protected XchangeDeliveryConfig config;

    public XchangeDeliveryConfig getConfig() {
        return config;
    }

    public void setConfig(XchangeDeliveryConfig config) {
        this.config = config;
    }
}
