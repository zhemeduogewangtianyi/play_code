package com.opencode.carrot.csearch.interfaces;

public interface Hook<T> {

    void hook(T t) throws Throwable;

}
