package com.opencode.jdk.thread.use.waitAndNotify02;

import com.opencode.jdk.thread.use.waitAndNotify02.runnable.Left;
import com.opencode.jdk.thread.use.waitAndNotify02.runnable.Mid;
import com.opencode.jdk.thread.use.waitAndNotify02.runnable.Right;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SourceObject sourceObject = new SourceObject();



        Left left = new Left(sourceObject);
        Thread leftT = new Thread(left);
        leftT.setName("left - ");
        leftT.start();

        Right right = new Right(sourceObject);
        Thread rightT = new Thread(right);
        rightT.setName("right - ");
        rightT.start();

        Mid mid = new Mid(sourceObject);
        Thread midT = new Thread(mid);
        midT.setName("mid - ");
        midT.start();


    }

}
