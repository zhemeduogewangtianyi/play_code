package com.opencode.dingding.entity.response.websocket.subscribe;

import lombok.Data;

@Data
public class SubscribeResponseHeaders {

    private String dt;
    private String authRouteLogicUnit;
    private String regSid;
    private String regUid;
    private String authRouteUnit;
    private String mid;
    private String sid;

}
