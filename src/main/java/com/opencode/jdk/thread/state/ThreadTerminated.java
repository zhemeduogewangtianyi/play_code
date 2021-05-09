package com.opencode.jdk.thread.state;

public class ThreadTerminated {

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        t.start();
        t.stop();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        t1.start();

        while(true){
            try {
                Thread.sleep(1000);
                System.out.println("t : " + t.getState());
                System.out.println("t1 : " + t1.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
