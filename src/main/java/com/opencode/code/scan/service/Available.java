package com.opencode.code.scan.service;

public interface Available<T> {

    default boolean available(T t){
        return false;
    }

}
