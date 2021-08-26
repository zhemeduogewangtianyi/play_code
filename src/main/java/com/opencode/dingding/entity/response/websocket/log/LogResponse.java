package com.opencode.dingding.entity.response.websocket.log;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogResponse extends WebSocketResponse {

    private Integer code;
    private LogResponseHeaders headers;

}
