package com.opencode.jdk.thread.pool.self;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

//        CarrotThreadPoolExecute execute = new CarrotThreadPoolExecute(5,10,new ArrayBlockingQueue<>(10));
        RabbitThreadPoolExecutor execute = new RabbitThreadPoolExecutor(5,10,new ArrayBlockingQueue<>(10));
        int count = 0;
        while(count < 10000){
            execute.execute(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info(Thread.currentThread().getName());
                }
            });
            count++;
        }
    }

}
