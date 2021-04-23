package com.opencode.code.scan.service;

public interface Processor<T> extends Support<T>,Available<T>,Hook<T>{

    Object process(T t);

}
