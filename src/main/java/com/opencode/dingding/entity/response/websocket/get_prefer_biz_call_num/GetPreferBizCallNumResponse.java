package com.opencode.dingding.entity.response.websocket.get_prefer_biz_call_num;

import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetPreferBizCallNumResponse extends WebSocketResponse {

    private GetPreferBizCallNumResponseHeaders headers;
    private GetPreferBizCallNumResponseBody body;
    private Integer code;

}
