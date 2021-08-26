package com.opencode.dingding.entity.response.websocket.reg;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegResponse extends WebSocketResponse {

    private RegResponseHeaders headers;

    private RegResponseBody body;

    private Integer code;

}
