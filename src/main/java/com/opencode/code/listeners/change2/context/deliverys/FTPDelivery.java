package com.opencode.code.listeners.change2.context.deliverys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FTPDelivery extends BaseDelivery {

    private String ip;

    private Integer port;

    private String username;

    private String password;

    private String changeDir;


}
