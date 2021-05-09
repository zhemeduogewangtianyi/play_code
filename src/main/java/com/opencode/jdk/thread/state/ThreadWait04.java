package com.opencode.jdk.thread.state;

public class ThreadWait04 {

    public static void main(String[] args) {

        Object o = new Object();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(o){
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

        while (true) {
            try {
                /**
                    线程 t 启动后获取到 资源 o，直接调用 o.wait()
                    线程直接进入等待状态
                 * */
                Thread.sleep(1000);
                System.out.println(t.getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
