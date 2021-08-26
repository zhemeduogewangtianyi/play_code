package com.opencode.dingding.interfaces.impl.websocket_route;

import com.opencode.dingding.abstracts.AbstractResponseRoute;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.entity.response.websocket.get_switch_status.GetSwitchStatusResponse;

public class GetSwitchStatusResponseRoute extends AbstractResponseRoute {

    @Override
    public WebSocketResponse route(ResponseContext context, Class<WebSocketResponse> cls) {
        return super.route(context,cls);
    }

    @Override
    public Class<WebSocketResponse> support(ResponseContext context) {
        Class<WebSocketResponse> cls;
        if(!GetSwitchStatusResponse.class.equals(cls = super.support(context))){
            return null;
        }
        return cls;
    }

}
