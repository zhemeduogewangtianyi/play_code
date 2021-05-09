package com.opencode.lock.aqs.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class SemaphoreMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreMain.class);

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                LOGGER.info("enter rejected !");
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2,true);

        for(int i = 0 ; i < 10000 ; i++){
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        LOGGER.info(finalI + " 来了");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                }
            });
        }
        executor.shutdown();

    }

}
