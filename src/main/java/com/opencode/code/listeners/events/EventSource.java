package com.opencode.code.listeners.events;

public class EventSource<T> {

    private final T source;

    private final long timeStamp;

    public EventSource(T source) {
        if(source == null){
            throw new RuntimeException("source can't null !");
        }
        this.source = source;
        this.timeStamp = System.currentTimeMillis();
    }

    public T getSource() {
        return source;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
