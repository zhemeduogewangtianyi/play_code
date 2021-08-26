package com.opencode.dingding.interfaces;

public interface Supported<T,R> {

    default Class<R> support(T t){
        return null;
    }

}
