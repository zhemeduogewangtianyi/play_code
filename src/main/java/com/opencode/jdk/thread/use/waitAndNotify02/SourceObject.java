package com.opencode.jdk.thread.use.waitAndNotify02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceObject.class);

    private Object left = new Object();
    private Object right = new Object();

    private boolean isWork = false;

    public void left(){

        synchronized (left){
            if(isWork){
                right.notify();
                try {
                    left.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String name = Thread.currentThread().getName();
            LOGGER.info(name);
            isWork = false;
        }
    }

    public void right(){

        synchronized (right){
            if(!isWork){
                try {
                    left.notify();
                    right.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String name = Thread.currentThread().getName();
            LOGGER.info(name);
            isWork = true;
            right.notify();
        }
    }


}
