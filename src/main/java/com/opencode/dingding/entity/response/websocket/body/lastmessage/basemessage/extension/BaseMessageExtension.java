package com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage.extension;

import lombok.Data;

@Data
public class BaseMessageExtension {

    private String lastTime;
    private String recordId;
    private String targetNicks;
    private String targetUids;
    private String callerNick;
    private String beginTime;
    private String callerId;
    private String sessionId;
    private String callType;
    private String status;
    private String dingpan_message_tag;

}

