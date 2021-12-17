package com.opencode.mysql.binlog.client;

import com.opencode.mysql.binlog.interfaces.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BinaryLogClient {

    /**
        https://dev.mysql.com/doc/internals/en/sending-more-than-16mbyte.html
        mysql 数据包限制
    */
    private static final int MAX_PACKET_LENGTH = 16777215;

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryLogClient.class);

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;

    private List<EventListener> eventListeners = new CopyOnWriteArrayList<>();


    public BinaryLogClient(String hostname, int port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public List<EventListener> getEventListeners() {
        return Collections.unmodifiableList(eventListeners);
    }

    public void registerEventListener(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public void unregisterEventListener(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

}
