package com.opencode.mysql.binlog.interfaces;

import com.opencode.mysql.binlog.event.Event;

public interface EventListener {

    void onEvent(Event event);

}
