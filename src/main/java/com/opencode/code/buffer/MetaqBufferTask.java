package com.opencode.code.buffer;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MetaqBufferTask extends Thread {

    private static final ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(10000);

    private final AtomicBoolean status = new AtomicBoolean(true);

    private Long timeOut;

    private Long lastRunTime;

    private final Integer bufferSize;

    private boolean off = true;

    private final File redis;

    public MetaqBufferTask(String name, Long timeOut, Integer bufferSize , File redis) {
        super(name);
        this.timeOut = timeOut;
        this.lastRunTime = System.currentTimeMillis();
        this.bufferSize = bufferSize;
        this.redis = redis;
    }

    public final boolean shutdown(){
        this.off = false;
        return true;
    }

    private boolean lookUpStatus(){
        return status.get();
    }

    private void flag(boolean flag){
        status.set(flag);
    }

    @Override
    public void run() {

        while(off){
            try{
                if(buffer.size() >= bufferSize){
                    process();
                }
                if(System.currentTimeMillis() - timeOut >= lastRunTime){
                    process();
                }

                Thread.sleep(ThreadLocalRandom.current().nextInt(50) + 10);

            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    public void push(String data) throws Exception {
        buffer.put(data);
        if(buffer.size() >= bufferSize && lookUpStatus()){
            process();
        }
    }

    private void process() throws Exception {
        if(lookUpStatus()){
            flag(false);
            rpushByPipeline();
            this.lastRunTime = System.currentTimeMillis();
            flag(true);
        }
    }

    private void rpushByPipeline() throws Exception {
        int batch = buffer.size() > bufferSize ? bufferSize : buffer.size();
        List<String> temp = new ArrayList<>(batch);
        for(int i = 0 ; i < batch ; i ++){
            String pop = buffer.take();
            if(StringUtils.isEmpty(pop)){
                return;
            }
            temp.add(pop);
        }
//        redis.rpushByPipeline(DataQueueSupport.getReportBizDataQueue(), temp.toArray(new String[]{}));
        FileOutputStream fos = new FileOutputStream(redis,true);
        PrintWriter pw = new PrintWriter(fos,true);
        for(String s : temp){
            if(StringUtils.isEmpty(s)){
                continue;
            }
            pw.println(s);
        }
        pw.println();
        pw.close();
        fos.close();

    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public Long getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Long lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public boolean isOff() {
        return off;
    }
}
