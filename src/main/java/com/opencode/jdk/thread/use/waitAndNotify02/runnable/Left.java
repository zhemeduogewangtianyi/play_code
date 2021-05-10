package com.opencode.jdk.thread.use.waitAndNotify02.runnable;

import com.opencode.jdk.thread.use.waitAndNotify02.SourceObject;

public class Left implements Runnable{

    private SourceObject sourceObject;

    public Left(SourceObject sourceObject) {
        this.sourceObject = sourceObject;
    }

    @Override
    public void run() {
        while(true){
            sourceObject.left(Thread.currentThread());
        }

    }

}
