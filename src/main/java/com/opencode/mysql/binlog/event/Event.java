package com.opencode.mysql.binlog.event;

import com.opencode.mysql.binlog.interfaces.EventData;
import com.opencode.mysql.binlog.interfaces.EventHeader;

import java.io.Serializable;

public class Event implements Serializable {

    private EventHeader header;
    private EventData data;

    public Event(EventHeader header, EventData data) {
        this.header = header;
        this.data = data;
    }

    public EventHeader getHeader() {
        return header;
    }

    public EventData getData() {
        return data;
    }

}
