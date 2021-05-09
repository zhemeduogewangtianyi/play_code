package com.opencode.jdk.thread.use.yield;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class YieldRunnable implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(YieldRunnable.class);

    private boolean isYield;

    public YieldRunnable(boolean isYield) {
        this.isYield = isYield;
    }

    @Override
    public void run() {

        AtomicInteger count = new AtomicInteger(1000);
        long start = System.currentTimeMillis();
        for(int i = 0 ; i < 1000 ; i++){
            if(isYield){
                Thread.yield();
            }
            count.decrementAndGet();
        }
        long time = System.currentTimeMillis() - start;
        String name = Thread.currentThread().getName();
        LOGGER.info(isYield ? "yield : name : " + name + " time : " + time + " ----------- " : "not yield : name : " + name + " time : " + time);
    }

}
