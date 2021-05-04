package com.opencode.code.listeners.change2.interfaces;

import com.opencode.code.listeners.change2.event.Event;

public interface Listener<T extends Event<T>> {

    boolean onEvent(T t);

}
