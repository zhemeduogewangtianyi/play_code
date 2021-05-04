package com.opencode.code.listeners.change2.tpl.base;

import com.alibaba.fastjson.JSON;
import com.opencode.code.listeners.change2.interfaces.Handle;
import com.opencode.code.listeners.change2.interfaces.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class BaseInterceptor<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);

    private final List<Interceptor<T>> interceptors = Collections.synchronizedList(new ArrayList<>());

    private Integer index = -1;

    protected Object applyPre(Handle<T> handle, T t) {
        for(int i = (index = 0) ; index < interceptors.size() ; index++ ){
            Interceptor<T> interceptor = interceptors.get(index);
            if(condition(interceptor,t)){
                return interceptor.pre(handle , t);
            }
        }
        return null;
    }

    protected void applyPost(Handle<T> handle , T t , Object result) throws Throwable {
        for(int i = interceptors.size() - 1 ; i >= 0 ; i-- ){
            Interceptor<T> interceptor = interceptors.get(i);
            if(condition(interceptor,t)){
                interceptor.hook(handle , t);
                interceptor.post(handle,t,result);
            }
        }
    }

    protected void applyAfter(Handle<T> handle , T t , Throwable e) {
        for(int i = index ; i >= 0 ; i--){
            Interceptor<T> interceptor = interceptors.get(i);
            if(condition(interceptor,t)){
                try {
                    interceptor.after(handle,t,e);
                } catch (Throwable throwable) {
                    LOGGER.error("Runtime Exception ! context is : {}" , JSON.toJSONString(t) , e);
                }
            }
        }
    }

    protected synchronized void setInterceptors(List<Interceptor<T>> interceptors) {
        interceptors.sort(new Comparator<Interceptor<T>>() {
            @Override
            public int compare(Interceptor<T> o1, Interceptor<T> o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        this.interceptors.addAll(interceptors);
    }

    private boolean condition(Interceptor<T> interceptor , T t) {
        if(!interceptor.available(t)){
            return false;
        }
        if(!interceptor.support(t)){
            return false;
        }
        return true;
    }

}
