package com.opencode.jdk.thread.state;

public class ThreadNew01 {

    public static void main(String[] args){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("new a thread");
            }
        });

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
