package com.opencode.scheduler.demo;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangjibo
 * @date 2021/10/9 10:05 上午
 * @description: TODO
 */
@Slf4j
public class TimeWheel {

    /**
     * 当前刻度
     */
    private AtomicInteger rollingIndex;

    /**
     * 时间轮刻度
     */
    private int wheelScale;

    /**
     * 时间轮转动间隔
     */
    private long interval;

    /**
     * 起始时间
     */
    private long startTime;

    /**
     * 外面一层轮子
     */
    private final TimeWheel outLayerWheel;

    /**
     * 单独线程池执行定时轮转
     */
    private final Timer timer = new Timer();

    private final PriorityQueue<ITimerWheelTask>[] taskQueues;

    public static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);


    public TimeWheel(TimeWheel outLayerWheel, long interval, int scale, Date startTime) {
        this.wheelScale = scale;
        this.interval = interval;
        this.outLayerWheel = outLayerWheel;
        this.startTime = startTime.getTime();
        this.rollingIndex = new AtomicInteger(0);

        taskQueues = new PriorityQueue[scale];
        for (int i = 0; i < taskQueues.length; i++) {
            taskQueues[i] = new PriorityQueue<>(Comparator.comparingLong(ITimerWheelTask::getExecutingTime));
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(interval == 60000){
                    log.info("分钟轮运行一次");
                }
                if (rollingIndex.incrementAndGet() == wheelScale) {
                    rollingIndex.set(0);
                }
                if (outLayerWheel == null) {
                    executeHeadingTask(taskQueues[rollingIndex.get()]);
                } else {
                    PriorityQueue<ITimerWheelTask> taskQueue = taskQueues[rollingIndex.get()];
                    deliveryTasks(taskQueue);
                }
            }
        }, startTime, interval);
    }

    /**
     * 执行头部任务
     *
     * @param taskQueue
     */
    private void executeHeadingTask(PriorityQueue<ITimerWheelTask> taskQueue) {
        while (true) {
            ITimerWheelTask task = taskQueue.peek();
            if (task == null) {
                return;
            }
            long t1 = task.getExecutingTime();
            long t2 = System.currentTimeMillis();
            log.info("execute time :{}, current time: {}", t1, t2);
            if (task.getExecutingTime() >= System.currentTimeMillis()) {
                TimeWheel.executorService.submit(() -> task.executeTask());
                taskQueue.poll();
                continue;
            }
            return;
        }

    }

    /**
     * 投递
     *
     * @param taskQueue
     */
    private void deliveryTasks(PriorityQueue<ITimerWheelTask> taskQueue) {
        while (true) {
            ITimerWheelTask task = taskQueue.peek();
            if (task == null) {
                return;
            }
            // 运行时间已经过去了
            if (task.getExecutingTime() <= System.currentTimeMillis()) {
                TimeWheel.executorService.submit(() -> task.executeTask());
                taskQueue.poll();
                continue;
            }
            // 运行时间在下次周期前
            if (task.getExecutingTime() + this.interval >= System.currentTimeMillis()) {
                outLayerWheel.attachTask(task);
                taskQueue.poll();
                continue;
            }
            return;
        }
    }

    /**
     * 挂载任务
     *
     * @param task
     */
    public void attachTask(ITimerWheelTask task) {
        if (task == null) {
            return;
        }
        long execTime = task.getExecutingTime();
        long currentTime = System.currentTimeMillis();
        // 运行时间已经过去了
        if (execTime <= currentTime || execTime <= startTime) {
            TimeWheel.executorService.submit(() -> task.executeTask());
            return;
        }
        // 运行时间在当前时间和下个周期之间
        if (execTime + interval <= currentTime) {
            // 用外层轮来挂载
            if (outLayerWheel != null) {
                outLayerWheel.attachTask(task);
            }
            // 无外层轮，直接执行
            else {
                TimeWheel.executorService.submit(() -> task.executeTask());
            }
            return;
        }
        // 挂载在当前轮
        long timeDiff = execTime - startTime;
        long index = (timeDiff / interval) % wheelScale;
        taskQueues[(int) index].add(task);
    }

    public static void main(String[] args) throws ParseException {
        TimeWheel secondWheel = new TimeWheel(null, 1000, 60, new Date());
        TimeWheel minuteWheel = new TimeWheel(secondWheel, 1000 * 60, 60, new Date());
        addTask(minuteWheel, Calendar.MINUTE, 2);
        addTask(minuteWheel, Calendar.MINUTE, 3);
        addTask(minuteWheel, Calendar.MINUTE, 4);
    }

    private static void addTask(TimeWheel wheel, int type, int delay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(type, delay);
        wheel.attachTask(new ITimerWheelTask() {
            @Override
            public long getExecutingTime() {
                return calendar.getTimeInMillis();
            }

            @Override
            public void executeTask() {
                System.out.println(calendar.getTimeInMillis() + "");
            }
        });
    }


}
