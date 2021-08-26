package com.opencode.dingding.entity.response.websocket.get_confirm_status_info;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetConfirmStatusInfoResponse extends WebSocketResponse {

    private Integer code;
    private GetConfirmStatusInfoResponseHeaders headers;
    private Map<String,Object> body;

}
