package com.opencode.code.cpiple.interfaces;

public interface Support<T> {

    default boolean support(T t){
        return false;
    }

}
