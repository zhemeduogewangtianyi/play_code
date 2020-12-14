package com.opencode.code.timewheel;

public class CircularArray {

    public static void main(String[] args) {
        CircularArray circularArray = new CircularArray(10);

//        System.err.println(circularArray.rear + " " + circularArray.front);

        for (int i = 0; i < circularArray.datas.length; i++) {
            boolean b = circularArray.enQueue(new String(i + " names"));
            System.out.println(b + " " + circularArray.cursor);
        }

        for (int i = 0; i < circularArray.datas.length; i++) {
            Object o = circularArray.deQueue();
            System.out.println(o + " " + circularArray.cursor);
        }

//        System.err.println(circularArray.rear + " " + circularArray.front);

//        for (int i = 0; i < circularArray.datas.length; i++) {
//            boolean b = circularArray.enQueue(new String(i + " names"));
//            System.out.println(b);
//        }
    }

    private int cursor;

    private final Object[] datas;

    private int front;

    private int rear;

    public CircularArray(int length) {
        this.cursor = 0;
        this.datas = new Object[length];
        this.front = 0;
        this.rear = 0;
    }

    public synchronized boolean enQueue(Object obj) {
        if (isFull()) {
            return false;
        }

        this.datas[this.rear] = obj;
        this.cursor++;
        this.rear = (this.rear + 1) % this.datas.length;
        return true;
    }

    public synchronized Object deQueue() {
        if (isEmpty()) {
            return null;
        }

        Object res = this.datas[this.front];
//        this.datas[this.front] = null;
        this.front = (this.front + 1) % this.datas.length;
        this.cursor--;
        return res;
    }

    public boolean isEmpty() {
        return this.front == this.rear;
    }

    public boolean isFull() {
        /*
             size = 10
                ->  front = 0
                    ->  rear = 2 + 1 = 3
                        ->  3 % 10 = 1
                            ->  1 == 0
         */

        int grooveIndex = (this.rear + 1) % this.datas.length;

        return grooveIndex == this.front;

    }
}

