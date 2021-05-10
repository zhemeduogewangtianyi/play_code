package com.opencode.jdk.thread.use.waitAndNotify02.runnable;

import com.opencode.jdk.thread.use.waitAndNotify02.SourceObject;

public class Mid implements Runnable{

    private SourceObject sourceObject;

    public Mid(SourceObject sourceObject) {
        this.sourceObject = sourceObject;
    }

    @Override
    public void run() {
        while(true){
            sourceObject.mid(Thread.currentThread());
        }

    }

}
