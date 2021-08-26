package com.opencode.dingding.entity.response.websocket.subscribe;

import com.opencode.dingding.abstracts.AbstractResponseRoute;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.interfaces.impl.websocket_route.SubscribeResponseRoute;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubscribeResponse extends WebSocketResponse {

    private SubscribeResponseBody body;

    private SubscribeResponseHeaders headers;

    protected Integer code;


    public static void main(String[] args) {
        AbstractResponseRoute handle = new SubscribeResponseRoute();

        String data = "{\"headers\":{\"dt\":\"j\",\"auth-route-logic-unit\":\"VIP001\",\"reg-sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\",\"reg-uid\":\"570085753@dingding\",\"auth-route-unit\":\"HZ\",\"mid\":\"a5ba001b 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":{\"unitName\":\"HZ\",\"cookie\":\"auth-route-logic-unit=VIP001;auth-route-unit=HZ\",\"timestamp\":1629801278605,\"isFromChina\":true}}";
        Class<WebSocketResponse> cls;
        ResponseContext context = new ResponseContext(data,new CheckLoginMessage());
        if((cls = handle.support(context)) != null){
            WebSocketResponse response = handle.route(context, cls);
            System.out.println(response);
        }
    }


}
