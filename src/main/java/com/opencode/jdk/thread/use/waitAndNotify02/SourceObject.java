package com.opencode.jdk.thread.use.waitAndNotify02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceObject.class);

    private Object o = new Object();

    private Thread t;

    private AlternateNode node = new AlternateNode();

    public void left(Thread t){

        synchronized (o){
//            if(t == this.t){
            if(t == node.next().getT()){
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            this.t = t;
            node.add(t);

            String name = Thread.currentThread().getName();
            LOGGER.info(name);
            o.notify();
        }
    }

    public void right(Thread t){

        synchronized (o){
//            if(t == this.t){
            if(t == node.next().getT()){
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            this.t = t;
            node.add(t);

            String name = Thread.currentThread().getName();
            LOGGER.info(name);
            o.notify();
        }
    }

    public void mid(Thread t){

        synchronized (o){
//            if(t == this.t){
            if(t == node.next().getT()){
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            this.t = t;
            node.add(t);

            String name = Thread.currentThread().getName();
            LOGGER.info(name);
            o.notify();
        }
    }


}
