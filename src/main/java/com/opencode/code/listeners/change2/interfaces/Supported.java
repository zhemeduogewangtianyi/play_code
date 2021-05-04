package com.opencode.code.listeners.change2.interfaces;

public interface Supported<T> {

    default boolean support(T t){
        return true;
    }

}
