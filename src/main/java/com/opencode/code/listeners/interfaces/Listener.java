package com.opencode.code.listeners.interfaces;

import com.opencode.code.listeners.events.EventSource;

public interface Listener<E extends EventSource> {

    void onEvent(E event);

}
