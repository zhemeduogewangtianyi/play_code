package com.opencode.code.listeners.change2.context.deliverys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SLSDelivery extends BaseDelivery {

    private String endPoint;

    private Integer accessKeyId ;

    private String accessKeySecret ;

}
