package com.opencode.jdk.thread.use.waitAndNotify02.runnable;

import com.opencode.jdk.thread.use.waitAndNotify02.SourceObject;

public class Right implements Runnable {

    private SourceObject sourceObject;

    public Right(SourceObject sourceObject) {
        this.sourceObject = sourceObject;
    }

    @Override
    public void run() {
        while(true){
            sourceObject.right();
        }
    }

}
