package com.opencode.code.listeners.change2.interfaces;

import org.apache.camel.Ordered;

public interface Handle<T> extends Supported<T> , Available<T> , Ordered {

    Object handle(T e) throws Throwable;

}
