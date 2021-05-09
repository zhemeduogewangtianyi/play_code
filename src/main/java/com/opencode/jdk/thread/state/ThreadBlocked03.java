package com.opencode.jdk.thread.state;

public class ThreadBlocked03 {


    public static void main(String[] args){

        Object o = new Object();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o){
                    try {
                        while(true){
                            //占坑不拉
                            Thread.sleep(10000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o){
                    //傻傻的阻塞
                }
            }
        });

        t1.start();

        while(true){
            try {
                /**
                    线程 t 拿到 o 这个资源后不释放锁
                    线程 t1 启动后就会阻塞
                 * */

                Thread.sleep(1000);
                System.out.println(t1.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
