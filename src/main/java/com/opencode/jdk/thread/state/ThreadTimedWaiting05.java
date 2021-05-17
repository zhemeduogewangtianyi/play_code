package com.opencode.jdk.thread.state;

import java.util.concurrent.locks.LockSupport;

public class ThreadTimedWaiting05 {

    public static void main(String[] args) {

        Object o = new Object();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        /**

                         (一个线程在一个特定的等待时间内等待另一个线程会在这个状态完成一个动作)

                         Thread#sleep() --- √

                         Object#wait() 并加了超时参数 --- √

                         Thread#join() 并加了超时参数 --- √

                         LockSupport#parkNanos() --- √

                         LockSupport#parkUntil()
                         */


                        Thread.sleep(50);
                        synchronized (o){
                            o.wait(50);
                        }
                        Thread.currentThread().join(50);
                        LockSupport.parkNanos(50);
                        LockSupport.parkUntil(50);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println(111);
                    }
            }
        });

        t.start();

        while(true){
            try {
                Thread.sleep(1000);
                System.out.println(t.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
