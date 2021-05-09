package com.opencode.jdk.lock.mcs;

import com.opencode.jdk.lock.Lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class McsLock implements Lock {

    private AtomicReference<McsLock.Node> tail;

    private ThreadLocal<McsLock.Node> node;

    public McsLock() {
        this.tail = new AtomicReference<>();
        this.node = new ThreadLocal<Node>(){
            @Override
            protected Node initialValue() {
                return new McsLock.Node();
            }
        };
    }

    private static class Node{

        private volatile boolean locked;

        private Node next;

    }

    @Override
    public void lock() {
        Node node = this.node.get();
        Node preNode = tail.getAndSet(node);
        if(preNode == null){
            node.locked = false;
            return;
        }
        node.locked = true;
        preNode.next = node;
        while (node.locked){
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
        if(node != null){
            node.locked = true;
            node.next = null;
            return;
        }

        boolean b = tail.compareAndSet(node, null);
        if(b){
            return;
        }

        while(node.next == null){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
