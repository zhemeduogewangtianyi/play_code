package com.opencode.code.cpiple.interfaces;


public interface InterceptorInterface<T> extends Support<T> , Available<T> , Order {

    Object pre(HandleInterface<T> handle, T t) throws Throwable;

    Object post(HandleInterface<T> handle, T t,Object result) throws Throwable;

    Object hook(HandleInterface<T> handle, T t) throws Throwable;

    Object after(HandleInterface<T> handle, T t,Throwable e) throws Throwable;

}
