package com.opencode.dingding.entity.response.websocket;

import lombok.Getter;

public class WebSocketResponse {

    @Getter
    private String name;

    public void setName(Class<? extends WebSocketResponse> cls){
        String simpleName = cls.getSimpleName();
        this.name = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }

}
