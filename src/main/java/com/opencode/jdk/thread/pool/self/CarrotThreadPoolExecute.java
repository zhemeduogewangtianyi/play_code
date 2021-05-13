package com.opencode.jdk.thread.pool.self;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class CarrotThreadPoolExecute implements Executor {

    private AtomicInteger ctl = new AtomicInteger(0);

    private volatile int coreSize;

    private volatile int maxSize;

    private final BlockingQueue<Runnable> queue;

    public CarrotThreadPoolExecute(int coreSize, int maxSize, BlockingQueue<Runnable> queue) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.queue = queue;
    }

    @Override
    public void execute(Runnable command) {
        int i = ctl.get();

        //少于核心线程数，直接创建
        if(i < coreSize){
            if(!addWorker(command)){
                reject();
            }
            return;
        }

        //少于核心线程数，放入队列
        if(!queue.offer(command)){
            if(!addWorker(command)){
                reject();
            }
        }

    }

    private boolean addWorker(Runnable task){
        if(ctl.get() >= maxSize){
            return false;
        }

        Worker worker = new Worker(task);
        worker.thread.start();
        ctl.decrementAndGet();
        return true;

    }

    public void reject(){
        throw new RuntimeException("Error ! ctl.count : " + ctl.get() + " workQueue.size : " + queue.size());
    }


    private final class Worker implements Runnable {

        final Thread thread;
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
                    if(ctl.get() > coreSize){
                        break;
                    }
                    task = null;
                }
            }finally {
                ctl.incrementAndGet();
            }
        }

        public Runnable getTask() {
            for(;;){
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
