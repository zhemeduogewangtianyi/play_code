package com.opencode.code.listeners.change2.context.deliverys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDelivery {

    private Long deliveryId;

    private String name;

    private Integer type;

    private Integer status;

    private FTPDelivery ftpDelivery;

    private SFTPDelivery sftpDelivery;

    private ODPSDelivery odpsDelivery;

    private HTTPDelivery httpDelivery;

    private OSSDelivery ossDelivery;

    private SLSDelivery slsDelivery;

}
