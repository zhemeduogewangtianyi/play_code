package com.opencode.code.listeners.change2.interfaces;

import org.apache.camel.Ordered;

import java.util.ArrayDeque;
import java.util.Deque;


public interface Interceptor<T> extends Supported<T> , Available<T> , Ordered {

    ThreadLocal<Deque<Object>> threadLocal = new ThreadLocal<Deque<Object>>(){
        @Override
        protected Deque<Object> initialValue() {
            return new ArrayDeque<>();
        }
    };

    Object pre(Handle<T> handle , T context);

    void post(Handle<T> handle , T context ,Object result) throws Throwable;

    Object after(Handle<T> handle , T context , Throwable e) throws Throwable;

    void hook(Handle<T> handle , T context);
}
