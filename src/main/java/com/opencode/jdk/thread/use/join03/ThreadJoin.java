package com.opencode.jdk.thread.use.join03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadJoin {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadJoin.class);

    public static void main(String[] args) {

        LOGGER.info("main start !");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("t start");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("t end");
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("main end !");


    }

}
