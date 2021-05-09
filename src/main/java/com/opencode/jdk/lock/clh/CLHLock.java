package com.opencode.jdk.lock.clh;

import com.opencode.jdk.lock.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLHLock.class);

    private final ThreadLocal<CLHLock.Node> prev;
    private final ThreadLocal<CLHLock.Node> node;
    private final AtomicReference<CLHLock.Node> tail ;

    private static class Node{
        private volatile boolean locked;
    }

    public CLHLock() {
        this.prev = ThreadLocal.withInitial(() -> null);
        this.node = ThreadLocal.withInitial(() -> new CLHLock.Node());
        this.tail = new AtomicReference<>(new CLHLock.Node());
    }

    @Override
    public void lock() {
        Node node = this.node.get();

        node.locked = true;
        Node prev_node = this.tail.getAndSet(node);
        this.prev.set(prev_node);
        while(prev_node.locked){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void unlock() {
        Node node = this.node.get();
        node.locked = false;
        this.node.set(this.prev.get());
    }

}
