package com.opencode.jdk.lock.clh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLHHandle {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLHHandle.class);

    private static CLHLock lock = new CLHLock();

    public String eat(Integer i){
        lock.lock();
        try{
//            LOGGER.info("eat -> " + i);
            return "eat -> " + i;
        }finally {
            lock.unlock();
        }
    }

    public void lash(String prev){
        lock.lock();
        LOGGER.info(prev + " --- lash -> ");
        lock.unlock();
    }

}
