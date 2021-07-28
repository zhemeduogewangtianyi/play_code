package com.opencode.carrot.csearch.interfaces;

public interface Available<T> {

    default boolean available(T t){
        return true;
    }

}
