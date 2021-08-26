package com.opencode.dingding.entity.response.websocket.create_temp_session_info;

import com.opencode.dingding.abstracts.AbstractResponseRoute;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.interfaces.impl.websocket_route.CreateTempSessionInfoResponseRoute;
import com.opencode.dingding.utils.DingResponseRouteUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTempSessionInfoResponse extends WebSocketResponse {

    private CreateTempSessionInfoResponseHeaders headers;
    private Integer code;
    private String body;

    public static void main(String[] args) {
        DingResponseRouteUtil.CALL_BACK_MAP.put("2a7d0020 0", CreateTempSessionInfoResponse.class);

        AbstractResponseRoute handle = new CreateTempSessionInfoResponseRoute();

        String data = "{\"headers\":{\"dt\":\"j\",\"mid\":\"2a7d0020 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":\"t-21fad179-7b77b9ecc8-2127ad1e-7d3b91-66658854-b50059dd-94d2-489a-ac25-3327fa3aef1f\"}";
        Class<WebSocketResponse> cls;
        ResponseContext context = new ResponseContext(data,new CheckLoginMessage());
        if((cls = handle.support(context)) != null){
            WebSocketResponse response = handle.route(context, cls);
            System.out.println(response);
        }
    }

}
