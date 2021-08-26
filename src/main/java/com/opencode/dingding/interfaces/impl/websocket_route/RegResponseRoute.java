package com.opencode.dingding.interfaces.impl.websocket_route;

import com.opencode.dingding.abstracts.AbstractResponseRoute;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.entity.response.websocket.reg.RegResponse;
import com.opencode.dingding.entity.response.websocket.reg.RegResponseHeaders;

public class RegResponseRoute extends AbstractResponseRoute {

    @Override
    public WebSocketResponse route(ResponseContext context, Class<WebSocketResponse> cls) {
        WebSocketResponse route = super.route(context, cls);
        RegResponse regResponse = (RegResponse) route;
        RegResponseHeaders headers = regResponse.getHeaders();
        String sid = headers.getSid();
        context.getMessage().setSid(sid);
        return route;
    }

    @Override
    public Class<WebSocketResponse> support(ResponseContext context) {
        Class<WebSocketResponse> cls;
        if(!RegResponse.class.equals(cls = super.support(context))){
            return null;
        }
        return cls;
    }

}
