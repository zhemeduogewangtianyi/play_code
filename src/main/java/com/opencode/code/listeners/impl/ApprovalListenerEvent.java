package com.opencode.code.listeners.impl;

import com.opencode.code.listeners.events.EventSource;

public class ApprovalListenerEvent extends EventSource<ApprovalParam> {

    public ApprovalListenerEvent(ApprovalParam source) {
        super(source);
    }

}
