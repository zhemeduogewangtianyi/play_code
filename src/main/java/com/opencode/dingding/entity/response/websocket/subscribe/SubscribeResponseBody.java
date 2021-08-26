package com.opencode.dingding.entity.response.websocket.subscribe;

import lombok.Data;

@Data
public class SubscribeResponseBody {

    private String unitName;
    private String cookie;
    private long timestamp;
    private boolean isFromChina;

}
