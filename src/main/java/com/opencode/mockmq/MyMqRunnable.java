package com.opencode.mockmq;

import com.opencode.code.listeners.change2.interfaces.Callback;


import java.util.concurrent.atomic.AtomicInteger;

public class MyMqRunnable implements Runnable{

    private Callback callback;

    private AtomicInteger ctl = new AtomicInteger(0);

    public MyMqRunnable(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        while (true) {
            int andIncrement = ctl.getAndIncrement();
            //Socket(RPC) HttClient
            String receiver = MqQueue.receiver(andIncrement);

            if(receiver == null){
                ctl.getAndDecrement();
            }
            if(receiver != null){
                callback.callback(receiver);
//                System.out.println(Thread.currentThread().getName() + " " + receiver);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
