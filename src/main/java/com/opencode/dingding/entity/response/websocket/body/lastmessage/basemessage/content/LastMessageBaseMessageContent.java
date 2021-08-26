package com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage.content;

import lombok.Data;

@Data
public class LastMessageBaseMessageContent {

    private TextContent textContent;
    private Integer contentType;

}
