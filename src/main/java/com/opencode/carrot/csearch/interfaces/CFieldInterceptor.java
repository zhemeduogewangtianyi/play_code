package com.opencode.carrot.csearch.interfaces;

import org.apache.dubbo.common.extension.SPI;

import java.util.ArrayDeque;
import java.util.Deque;

@SPI
public interface CFieldInterceptor<T> extends Supported<T> , Available<T> , Ordered{

    ThreadLocal<Deque<Object>> tl = new ThreadLocal<Deque<Object>>(){
        @Override
        protected Deque<Object> initialValue() {
            return new ArrayDeque<>();
        }
    };

    Object pre(T t) throws Throwable;

    void post(T t,Object result) throws Throwable;

    void pre(T t , Object result , Throwable e) throws Throwable;

    @Override
    int order();

}
