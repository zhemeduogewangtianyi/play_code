package com.opencode.code.scan.entity;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
public class RequestParam {

    private String name;

    private String type;

    private Long time;

    private Long startTime;

    private String ip;

    {
        try {
            ip = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private String configName;

}
