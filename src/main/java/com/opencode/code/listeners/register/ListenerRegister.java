package com.opencode.code.listeners.register;

import com.opencode.code.listeners.events.EventSource;
import com.opencode.code.listeners.interfaces.EventMulticaster;
import com.opencode.code.listeners.interfaces.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.*;

public class ListenerRegister implements EventMulticaster {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerRegister.class);

    private static final Set<Listener<?>> REGISTER_SET = Collections.synchronizedSet(new LinkedHashSet<>());

    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("listener-");
                    return t;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        LOGGER.error("retry put Runnable fail !" , e);
                    }
                }
            });

    @Override
    public void addListener(Listener<?> listener) {
        REGISTER_SET.add(listener);
    }

    @Override
    public void delListener(Listener<?> listener) {
        REGISTER_SET.remove(listener);
    }

    @Override
    public void publisherListener(final EventSource event) {
        for(Listener listener : REGISTER_SET){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    listener.onEvent(event);
                }
            });
        }

    }
}
