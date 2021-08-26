package com.opencode.dingding.interfaces;

public interface ResponseRoute<T,R> extends Supported<T ,R> {

    R route(T t , Class<R> cls);

}
