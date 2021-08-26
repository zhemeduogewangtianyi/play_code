package com.opencode.dingding.abstracts;

import com.alibaba.fastjson.JSONObject;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.interfaces.ResponseRoute;
import com.opencode.dingding.utils.DingResponseRouteUtil;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractResponseRoute implements ResponseRoute<ResponseContext, WebSocketResponse> {

    @Override
    public WebSocketResponse route(ResponseContext context, Class<WebSocketResponse> cls){
        String res = context.getRes();
        try{
            WebSocketResponse response = JSONObject.parseObject(res, cls);
            response.setName(cls);
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            DingResponseRouteUtil.delMid(res);
        }
    }

    @Override
    public Class<WebSocketResponse> support(ResponseContext context){
        if(context == null){
            return null;
        }
        if(context.getMessage() == null){
            return null;
        }
        String res = context.getRes();
        if(StringUtils.isEmpty(res)){
            return null;
        }
        Class<? extends WebSocketResponse> cls = DingResponseRouteUtil.checkMid(res);
        if(cls == null){
            return null;
        }

        return (Class<WebSocketResponse>) cls;
    }

}
