package com.opencode.code.cpiple.interfaces;

public interface Available<T> {

    default boolean available(T t){
        return true;
    }

}
