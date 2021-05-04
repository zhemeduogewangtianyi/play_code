package com.opencode.code.listeners.change2.context.deliverys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HTTPDelivery {

    private String url;

    private Integer header ;

    private String requestMethod ;

    private String body;

}
