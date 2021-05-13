package com.opencode.jdk.thread.pool.self;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RabbitThreadPoolExecutor implements Executor {

    private AtomicInteger ctl = new AtomicInteger(0);

    private volatile int coreSize;

    private volatile int maxSize;

    private final BlockingQueue<Runnable> worker;

    public RabbitThreadPoolExecutor(int coreSize, int maxSize, BlockingQueue<Runnable> worker) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.worker = worker;
    }

    @Override
    public void execute(Runnable command) {
        int i = ctl.get();
        if(i < coreSize){
            if(addWorker(command)){
                reject(command);
            }
            return;
        }

        if(!worker.offer(command)){
            if(!addWorker(command)){
                reject(command);
            }
        }

    }

    public boolean addWorker(Runnable command){
        if(ctl.get() >= maxSize){
            return false;
        }
        new Worker(command).thread.start();
        ctl.incrementAndGet();
        return true;
    }

    private void reject(Runnable r){
        try {
            worker.put(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error ! ctl : " + ctl.get() + " workerQueue size : " + worker.size());
        }

    }

    private final class Worker implements Runnable{

        Thread thread;

        Runnable task;

        public Worker(Runnable task) {
            this.thread = new Thread(this);
            this.task = task;
        }

        @Override
        public void run() {

            try{
                while(task != null || (task = getTask()) != null){
                    task.run();
                    if(ctl.get() >= coreSize){
                        break;
                    }
                    task = null;
                }
            }finally {
                ctl.decrementAndGet();
            }

        }

        public Runnable getTask() {
            for(;;){
                try {
                    return worker.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
