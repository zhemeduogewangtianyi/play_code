package com.opencode.code.listeners.change2.tpl.base;

import com.opencode.code.listeners.change2.interfaces.Handle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public abstract class BaseHandle<T> extends BaseInterceptor<T>{

    private final List<Handle<T>> handles = Collections.synchronizedList(new ArrayList<>());

    public abstract void execute(T t);

    protected Handle<T> findHandle(T t){
        for(Handle<T> handle : handles){
            if(!handle.available(t)){
                continue;
            }
            if(!handle.support(t)){
                continue;
            }
            return handle;
        }
        return null;
    }

    protected void setHandles(List<Handle<T>> handles){
        handles.sort(new Comparator<Handle<T>>() {
            @Override
            public int compare(Handle<T> o1, Handle<T> o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        this.handles.addAll(handles);
    }

}
