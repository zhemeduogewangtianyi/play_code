package com.opencode.carrot.csearch.interfaces;

public interface CFieldHandle<T,R> extends Supported<T>{

    R handle(T context);

}
