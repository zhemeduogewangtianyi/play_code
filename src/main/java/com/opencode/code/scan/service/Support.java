package com.opencode.code.scan.service;

public interface Support<T> {

    default boolean support(T t){
        return false;
    }

}
