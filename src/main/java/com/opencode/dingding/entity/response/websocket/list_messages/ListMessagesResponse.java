package com.opencode.dingding.entity.response.websocket.list_messages;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListMessagesResponse extends WebSocketResponse {

    private LimitMessagesResponseHeaders headers;
    private Integer code;
    private List<LimitMessagesResponseBody> body;

}
