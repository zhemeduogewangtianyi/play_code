package com.opencode.code.listeners.change2.event;

public class Event<T> {

    private T source;

    private Long createTime;

    public Event(T source) {
        if(source == null){
            throw new RuntimeException("source can't null !");
        }
        this.source = source;
        this.createTime = System.currentTimeMillis();
    }
}
