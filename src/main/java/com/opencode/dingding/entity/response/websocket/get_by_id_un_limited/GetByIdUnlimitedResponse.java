package com.opencode.dingding.entity.response.websocket.get_by_id_un_limited;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetByIdUnlimitedResponse extends WebSocketResponse {

    private GetByIdUnlimitedResponseHeaders headers;
    private Integer code;
    private GetByIdUnlimitedResponseBody body;

}
