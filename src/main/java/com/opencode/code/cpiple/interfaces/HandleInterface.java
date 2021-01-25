package com.opencode.code.cpiple.interfaces;

public interface HandleInterface<T> extends Support<T> , Available<T> {

    Object handle(T t);

}
