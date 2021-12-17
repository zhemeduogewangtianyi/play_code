package com.opencode.mysql.binlog.interfaces;

import com.opencode.mysql.binlog.enums.EventType;

import java.io.Serializable;

public interface EventHeader extends Serializable {

    long getTimestamp();
    EventType getEventType();
    long getServerId();
    long getHeaderLength();
    long getDataLength();

}
