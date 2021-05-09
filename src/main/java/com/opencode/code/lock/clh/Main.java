package com.opencode.code.lock.clh;

import java.util.concurrent.*;

public class Main {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10000), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) {

        CLHHandle clhHandle = new CLHHandle();

        for(int i = 0 ; i < 1000 ; i ++){
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    clhHandle.lash(clhHandle.eat(finalI));
                }
            });
        }


    }

}
