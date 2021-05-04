package com.opencode.code.listeners.interfaces;

import com.opencode.code.listeners.events.EventSource;

public interface EventMulticaster {

    void addListener(Listener<?> listener);

    void delListener(Listener<?> listener);

    void publisherListener(EventSource eventSource);

}
