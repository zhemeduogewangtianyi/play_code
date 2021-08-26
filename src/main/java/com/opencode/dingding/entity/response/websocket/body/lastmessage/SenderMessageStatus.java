package com.opencode.dingding.entity.response.websocket.body.lastmessage;

import lombok.Data;

@Data
public class SenderMessageStatus {

    private Integer unReadCount;
    private Integer totalCount;

}
