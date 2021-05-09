package com.opencode.jdk.lock.aqs.self;


import com.opencode.jdk.lock.Lock;

public class SyncLock implements Lock {

    private final Sync sync;

    public SyncLock() {
        this.sync = new Sync();
    }

    @Override
    public void lock() {
        sync.tryAcquire(1);
    }

    @Override
    public void unlock() {
        sync.tryRelease(1);
    }
}
