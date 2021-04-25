package com.opencode.code.buffer;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Test {

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadFactory() {
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

    private static Map<Integer, MetaqBufferTask> tasks = new ConcurrentHashMap<>();

    static{

        for(int i = 97 ; i < 102 ; i ++){
            MetaqBufferTask a =
                    new MetaqBufferTask("D:\\TEST\\" + (char)i + ".txt", 10000L, 200, new File("D:\\TEST\\" + (char)i + ".txt"));
            a.start();
            tasks.put(i , a);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            System.out.println(InetAddress.getLocalHost().getAddress().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        while(true){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try{
                            int i = ThreadLocalRandom.current().nextInt(5) + 97;
                            MetaqBufferTask metaqBufferTask = tasks.get(i);
                            metaqBufferTask.push("唧唧复唧唧，木兰当户织 ~ " + System.currentTimeMillis());
                            Thread.sleep(20);
                        }catch (Exception e){
                            e.printStackTrace();
                            continue;
                        }

                    }
                }
            });
            Thread.sleep(50);
        }


    }

}
