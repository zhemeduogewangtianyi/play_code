package com.opencode.dingding.entity.response.websocket.get_switch_status;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetSwitchStatusResponse extends WebSocketResponse {

    private Integer code;
    private GetSwitchStatusResponseHeaders headers;
    private Integer body;

}
