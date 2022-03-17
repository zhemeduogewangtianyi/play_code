package com.opencode.jdk.queue;


import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestQueue {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.put("1");

    }

}
