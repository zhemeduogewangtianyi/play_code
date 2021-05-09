package com.opencode.lock.aqs.self;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 1. Sync 类继承 AbstractQueuedSynchronizer，并重写方法：tryAcquire、
 tryRelease、isHeldExclusively。
 2. 这三个方法基本是必须重写的，如果不重写在使用的时候就会抛异常
 UnsupportedOperationException。
 3. 重写的过程也比较简单，主要是使用 AQS 提供的 CAS 方法。以预期值为 0，写
 入更新值 1，写入成功则获取锁成功。其实这个过程就是对 state 使用 unsafe
 本地方法，传递偏移量 stateOffset 等参数，进行值交换操作。
 unsafe.compareAndSwapInt(this, stateOffset, expect,
 update)
 4. 最后提供 lock、unlock 两个方法，实际的类中会实现 Lock 接口中的相应方法，
 这里为了简化直接自定义这样两个方法。
 * */
public class Sync extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        return compareAndSetState(0,1);
    }

    @Override
    protected boolean tryRelease(int arg) {
        setState(0);
        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }
}
