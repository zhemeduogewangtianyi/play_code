package com.opencode.code.listeners.change2.interfaces;

public interface Available<T> {

    default boolean available(T e){
        return true;
    }

}
