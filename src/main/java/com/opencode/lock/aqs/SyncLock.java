package com.opencode.lock.aqs;


import com.opencode.lock.Lock;

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
