package com.opencode.bug.moreThread;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test extends BaseI{

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    private final List<Base> list = new ArrayList<>();

    {
        list.add(new A());
        list.add(new B());
        list.add(new C());
    }

    public static void main(String[] args) {
//        CLHLock clhLock = new CLHLock();

        Test test = new Test();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        test.setList(test.list);

        for(int i = 0 ; i < 10000 ; i++){
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
//                    clhLock.lock();
//                    LOGGER.info(finalI + "");
//                    clhLock.unlock();
                    test.setList(test.list);
                    Context context = new Context();
                    context.setT(Thread.currentThread());
                    context.setName(finalI + "");
                    context.setUuid(UUID.randomUUID().toString());
                    context.setState(0);
                    test.a(context);
                    test.b(context);
                    test.c(context);
                }
            });
        }
        executor.shutdown();

    }

}

@Data
class Context{

    private String uuid;

    private String name;

    private Long id;

    private transient Thread t;

    private Integer state;

}

abstract class BaseI{

    private List<Base> list = Collections.synchronizedList(new ArrayList<>());

    private Lock look = new ReentrantLock();

    public void a(Context context){
        for(Base b : list){
            if(!b.support(context)){
                continue;
            }
            context.setName(context.getName() + " a , ");
            b.execute(context);
        }
        context.setState(1);
    }

    public void b(Context context){
        for(Base b : list){
            if(!b.support(context)){
                continue;
            }
            context.setName(context.getName() + "b , ");
            b.execute(context);
        }
        context.setState(2);
    }

    public void c(Context context){
        for(Base b : list){
            if(!b.support(context)){
                continue;
            }
            context.setName(context.getName() + "c , ");
            b.execute(context);
        }
        context.setState(3);
    }

    public void setList(List list){
        look.lock();
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });
        this.list = list;
        look.unlock();
    }



}

interface Base{
    void execute(Context context);

    boolean support(Context context);
}

class A implements Base {

    private static final Logger LOGGER = LoggerFactory.getLogger(A.class);

    @Override
    public void execute(Context context) {
        boolean b = Thread.currentThread().getId() == context.getT().getId();
        context.setName(context.getName() + " A.execute() ");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        LOGGER.info("AAAAA : " + b);
    }

    @Override
    public boolean support(Context context) {
        return context.getState() == 0;
    }
}

class B implements Base {

    private static final Logger LOGGER = LoggerFactory.getLogger(B.class);

    @Override
    public void execute(Context context) {
        boolean b = Thread.currentThread().getId() == context.getT().getId();
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setName(context.getName() + " B.execute() ");
//        LOGGER.info("BBBBB : " + b);
    }

    @Override
    public boolean support(Context context) {
        return context.getState() == 1;
    }
}

class C implements Base {

    private static final Logger LOGGER = LoggerFactory.getLogger(C.class);

    @Override
    public void execute(Context context) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean b = Thread.currentThread().getId() == context.getT().getId();
        context.setName(context.getName() + " C.execute() ");
        LOGGER.info("CCCCC : " + b + " " + JSON.toJSONString(context));
    }

    @Override
    public boolean support(Context context) {
        return context.getState() == 2;
    }
}