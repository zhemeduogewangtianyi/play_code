package com.opencode.mockmq;

import com.opencode.code.listeners.change2.interfaces.Callback;

public class Consumer {

    public static void startConsumer() {

        new Thread(new MyMqRunnable(new Callback() {
            @Override
            public void callback(Object o) {
                System.out.println(o);
            }
        })).start();

        new Thread(new MyMqRunnable(new Callback() {
            @Override
            public void callback(Object o) {
                System.out.println(o);
            }
        })).start();

        new Thread(new MyMqRunnable(new Callback() {
            @Override
            public void callback(Object o) {
                System.out.println(o);
            }
        })).start();

        new Thread(new MyMqRunnable(new Callback() {
            @Override
            public void callback(Object o) {
                System.out.println(o);
            }
        })).start();

        new Thread(new MyMqRunnable(new Callback() {
            @Override
            public void callback(Object o) {
                System.out.println(o);
            }
        })).start();


    }

}
