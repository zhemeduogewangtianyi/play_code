package com.opencode.dingding.interfaces.impl.websocket_route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.dingding.abstracts.AbstractResponseRoute;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.entity.response.websocket.get_confirm_status_info.GetConfirmStatusInfoResponse;
import com.opencode.dingding.entity.response.websocket.get_confirm_status_info.GetConfirmStatusInfoResponseHeaders;

public class GetConfirmStatusInfoResponseRoute extends AbstractResponseRoute {

    @Override
    public WebSocketResponse route(ResponseContext context, Class<WebSocketResponse> cls) {
        try{
            return super.route(context,cls);
        }catch(Exception e){
            JSONObject jsonObject = JSONObject.parseObject(context.getRes());
            Object headers = jsonObject.get("headers");
            Object code = jsonObject.get("code");
            Object body = jsonObject.get("body");
            JSONObject bodyMap = JSONObject.parseObject(body.toString());
            GetConfirmStatusInfoResponse response = new GetConfirmStatusInfoResponse();
            response.setName(response.getClass());
            response.setCode(code == null ? null : (Integer) code);
            response.setHeaders(headers == null ? null : JSONObject.parseObject(JSON.toJSONString(headers), GetConfirmStatusInfoResponseHeaders.class));
            response.setBody(bodyMap);
            return response;
        }
    }

    @Override
    public Class<WebSocketResponse> support(ResponseContext context) {
        Class<WebSocketResponse> cls;
        if(!GetConfirmStatusInfoResponse.class.equals(cls = super.support(context))){
            return null;
        }
        return cls;
    }

}
