package com.opencode.scheduler.demo2;

public interface ITimerWheelTask {

    /** 获取执行时间 */
    long getExecutingTime();

    /** 执行 */
    void execute();

}
