package com.opencode.code.listeners.change2.service.impl;

import com.opencode.code.listeners.change2.context.deliverys.FTPDelivery;
import com.opencode.code.listeners.change2.entity.daoObject.DeliveryDO;
import com.opencode.code.listeners.change2.enums.DeliveryTypeEnum;
import com.opencode.code.listeners.change2.service.DeliveryService;
import org.springframework.stereotype.Component;

@Component
public class DelliveryServiceImpl implements DeliveryService {

    @Override
    public DeliveryDO queryById(Long id) {

        DeliveryDO deliveryDO = new DeliveryDO();
        deliveryDO.setId(id);
        deliveryDO.setStatus(1);
        deliveryDO.setName("模板渲染");
        deliveryDO.setType(DeliveryTypeEnum.FTP.getType());

        FTPDelivery ftpDelivery = new FTPDelivery();
        ftpDelivery.setIp("127.0.0.1");
        ftpDelivery.setPort(21);
        ftpDelivery.setUsername("ftpuser");
        ftpDelivery.setPassword("ftpuser");
        ftpDelivery.setChangeDir("a/b/c");

        return deliveryDO;
    }

}
