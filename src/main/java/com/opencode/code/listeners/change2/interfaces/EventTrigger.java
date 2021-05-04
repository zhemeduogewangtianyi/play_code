package com.opencode.code.listeners.change2.interfaces;


import com.opencode.code.listeners.change2.event.Event;
import org.apache.camel.Ordered;

public interface EventTrigger<T extends Event<T>> extends Supported<T> , Available<T> , Ordered {

    boolean addListener(Listener<T> listener);

    boolean delListener(Listener<T> listener);

    boolean publisher(Event<T> event);

}
