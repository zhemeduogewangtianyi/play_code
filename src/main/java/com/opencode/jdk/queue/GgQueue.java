package com.opencode.jdk.queue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GgQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(GgQueue.class);

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private Object[] datas;

    private int default_capacity = 10;

    private int offset;

    public GgQueue() {
        this.datas = new Object[default_capacity];
    }

    private void enqueue(Object data){
        if (offset < datas.length) {
            datas[offset++] = data;
        }
        notEmpty.signal();
    }

    private Object dequeue(){
        Object data = datas[--offset];
        datas[offset]=null;
        notFull.signal();
        return data;
    }

    public boolean amplifyAdd(Object data) {

        enqueue(data);

        if (offset >= datas.length) {
            Object[] new_datas = new Object[offset << 1];
            for (int i = 0; i < datas.length; i++) {
                new_datas[i] = datas[i];
            }
            datas = new_datas;
        }

        return true;

    }

    public boolean offer(Object data) {

        if (offset < datas.length) {
            enqueue(data);
            return true;
        }
        return false;
    }

    public void put(Object data) throws InterruptedException {

        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();

        try{
            while(offset == datas.length){
                notFull.await();
            }
            enqueue(data);
        }finally {
            lock.unlock();
        }


    }

    public void add(Object data) throws Exception {
        if(offset < datas.length){
            datas[offset++] = data;
        }else{
            throw new Exception("full");
        }
    }

    public Object get(int index) {
        if (index > offset) {
            throw new IndexOutOfBoundsException();
        }
        return datas[index];
    }

    public Object take() throws InterruptedException {

        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try{
            while(offset == 0){
                notEmpty.await();
            }

            return dequeue();
        }finally {
            lock.unlock();
        }

    }

    public Object poll() throws InterruptedException {

        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();

        try{
            while(offset == 0){
                notEmpty.await();
            }

            Object data = datas[0];
            Object[] newDatas = new Object[datas.length - 1 > default_capacity ? datas.length - 1 : default_capacity];
            System.arraycopy(datas , 1 , newDatas , 0 , newDatas.length - 1 );
            datas = newDatas;
            offset--;
            notFull.signal();
            return data;
        }finally {
            lock.unlock();
        }

    }

    public int size() {
        return offset;
    }

    public static void main(String[] args) throws InterruptedException {

        GgQueue ggQueue = new GgQueue();

        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 30, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000), new ThreadFactory() {
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


        pool.execute(new Runnable() {
            @Override
            public void run() {

                while(true){
                    try {
                        Object take = ggQueue.poll();
                        LOGGER.info(take + " -- " + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        pool.execute(new Runnable() {

            int w = 0;

            @Override
            public void run() {
                while(true){
                    try {
                        ggQueue.put(w++);

                        ThreadLocalRandom.current().nextInt(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }

}
