package com.opencode.scheduler.demo2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ITimeWheel {

    private static final Logger log = LoggerFactory.getLogger(ITimeWheel.class);

    /** 当前刻度 */
    private AtomicInteger rollingIndex;

    /** 时间轮刻度 */
    private final int wheelScale;

    /** 转动间隔 */
    private final long interval;

    /** 开始时间 */
    private final long startTime;

    /** 外圈 */
    private final ITimeWheel outWheel;

    private final Timer timer;

    private PriorityBlockingQueue<ITimerWheelTask>[] taskQueues;

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            10,
            20,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    public ITimeWheel(ITimeWheel outWheel , long interval , int wheelScale , Date startTime) {

        this.outWheel = outWheel;
        this.interval = interval;
        this.wheelScale = wheelScale;

        this.startTime = startTime.getTime();
        this.rollingIndex = new AtomicInteger(0);

        this.taskQueues = new PriorityBlockingQueue[wheelScale];
        for(int i = 0 ; i < this.taskQueues.length ; i++){
            this.taskQueues[i] = new PriorityBlockingQueue<>(100 , Comparator.comparingLong(ITimerWheelTask::getExecutingTime));
        }

        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(interval == TimeUnit.SECONDS.toMillis(1)){
                    log.info("秒轮运行一次");
                }

                if(interval == TimeUnit.MINUTES.toMillis(1)){
                    log.info("分钟轮运行一次");
                }

                if (rollingIndex.incrementAndGet() == wheelScale) {
                    rollingIndex.set(0);
                }

                //没有外轮执行头部
                if (outWheel == null) {

                    executeHeadingTask(taskQueues[rollingIndex.get()]);

                }
                //有外轮先执行外轮
                else {
                    PriorityBlockingQueue<ITimerWheelTask> taskQueue = taskQueues[rollingIndex.get()];

                    deliveryTasks(taskQueue);
                }

            }
        } , startTime , interval);

    }

    void deliveryTasks(PriorityBlockingQueue<ITimerWheelTask> taskQueue){

        if(null == taskQueue){
            return;
        }

        while(true){

            ITimerWheelTask task = taskQueue.poll();

            if(task == null){
                return;
            }

            long t1 = task.getExecutingTime();
            long t2 = System.currentTimeMillis();

            //运行时间过去
            if(t1 <= t2){
                this.pool.submit(() -> task.execute());
                attachTask(task);
            }
            //运行时间在下个周期
            if(t2 > t1){
                this.attachTask(task);
            }
        }

    }

    public void executeHeadingTask(PriorityBlockingQueue<ITimerWheelTask> taskQueue){

        if(null == taskQueue){
            return;
        }

        while(true){

            ITimerWheelTask task = taskQueue.poll();
            if(null != task){

                long t1 = task.getExecutingTime();
                long t2 = System.currentTimeMillis();

                if(t1 >= t2){
                    this.pool.submit(() -> task.execute());
                    attachTask(task);
                }

            }else{

                return;

            }
        }

    }

    void attachTask(ITimerWheelTask task){

        if(task == null){
            throw new RuntimeException("task is null !");
        }

        long executingTime = task.getExecutingTime();
        long currentTime = System.currentTimeMillis();

        //已经过了执行时机，直接执行
        if(executingTime < currentTime || executingTime < this.startTime){
            this.pool.submit(() -> task.execute());
            return;
        }

        //在执行完毕和即将执行之间
        if(this.interval + executingTime <= currentTime){
            //存在外轮，挂在外轮
            if(this.outWheel != null){
                this.outWheel.attachTask(task);
            }
            //不存在外轮直接执行
            else{
                this.pool.submit(() -> task.execute());
            }

            return;
        }

        //下一个执行周期的，挂在当前轮子上面
        long timeDiff = executingTime - startTime;
        int index = (int)(timeDiff / interval) % wheelScale;
        taskQueues[index].add(task);

    }

    public static void main(String[] args) {
        ITimeWheel secondWheel = new ITimeWheel(null, TimeUnit.SECONDS.toMillis(1), 60, new Date());
        ITimeWheel minuteWheel = new ITimeWheel(secondWheel, TimeUnit.MINUTES.toMillis(1), 60, new Date());
        addTask(secondWheel , Calendar.SECOND , 1);
        addTask(minuteWheel , Calendar.MINUTE , 0);
        addTask(secondWheel , Calendar.SECOND , 0);

    }

    public static void addTask(ITimeWheel iTimeWheel , int type , int delay){
        Calendar calendar = Calendar.getInstance();
        calendar.set(type , delay);
        iTimeWheel.attachTask(new ITimerWheelTask() {
            @Override
            public long getExecutingTime() {
                return calendar.getTimeInMillis();
            }

            @Override
            public void execute() {
                System.out.println("1111");
            }
        });
    }

}
