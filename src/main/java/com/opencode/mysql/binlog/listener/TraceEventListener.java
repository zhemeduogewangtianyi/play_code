package com.opencode.mysql.binlog.listener;

import com.opencode.mysql.binlog.event.Event;
import com.opencode.mysql.binlog.interfaces.EventListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TraceEventListener implements EventListener {

    private final Logger LOGGER = Logger.getLogger(getClass().getSimpleName());

    @Override
    public void onEvent(Event event) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Received " + event);
        }
    }

}
