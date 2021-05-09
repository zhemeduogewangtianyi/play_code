package com.opencode.jdk.thread.use.yield01;

/**
    测试线程 yield 对线程运行耗时的影响

    yield 方法让出 CPU，但不一定，一定让出！。这种可能会用在一些同时启动的
    线程中，按照优先级保证重要线程的执行，也可以是其他一些特殊的业务场景（例
    如这个线程内容很耗时，又不那么重要，可以放在后面）。

 * */
public class ThreadYield {

    public static void main(String[] args) {

        /**
            启动 50 个线程进行，每个线程都进行 1000
            次的加和计算。其中 10 个线程会执行让出 CPU 操作。那么，如果让出 CPU 那 10
            个线程的计算加和时间都比较长，说明确实在进行让出操作。
         * */

        for(int i = 0 ; i < 50 ; i++){
            YieldRunnable runnable = i < 10 ? new YieldRunnable(true) : new YieldRunnable(false);
            new Thread(runnable).start();
        }

    }

}
