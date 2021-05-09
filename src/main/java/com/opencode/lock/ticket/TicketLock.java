package com.opencode.lock.ticket;

import com.opencode.lock.Lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock implements Lock {

    private AtomicInteger serverCount ;

    private AtomicInteger incrementTicket;

    private ThreadLocal<Integer> current;

    public TicketLock() {
        this.serverCount = new AtomicInteger(0);
        this.incrementTicket = new AtomicInteger(0);
        this.current = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };
    }

    @Override
    public void lock() {
        current.set(incrementTicket.getAndIncrement());
        while(serverCount.get() != current.get()){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void unlock() {
        serverCount.compareAndSet(current.get(),current.get() + 1);
        current.remove();
    }
}
