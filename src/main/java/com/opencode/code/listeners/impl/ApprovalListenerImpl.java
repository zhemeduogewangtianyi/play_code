package com.opencode.code.listeners.impl;

import com.alibaba.fastjson.JSON;
import com.opencode.code.listeners.interfaces.Listener;

public class ApprovalListenerImpl implements Listener<ApprovalListenerEvent> {

    @Override
    public void onEvent(ApprovalListenerEvent event) {
        ApprovalParam source = event.getSource();
        System.out.println(JSON.toJSONString(source));
    }

}
