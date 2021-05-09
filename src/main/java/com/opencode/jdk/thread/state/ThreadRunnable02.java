package com.opencode.jdk.thread.state;

public class ThreadRunnable02 {

    public static void main(String[] args){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){}
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
