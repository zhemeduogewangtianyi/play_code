package com.opencode.scheduler.demo;

/**
 * shi
 */
public interface ITimerWheelTask {

    /**
     * 获取执行时间
     *
     * @return
     */
    long getExecutingTime();

    /**
     * 执行任务
     */
    void executeTask();
}
