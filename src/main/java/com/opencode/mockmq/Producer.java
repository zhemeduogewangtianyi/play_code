package com.opencode.mockmq;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Producer {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Consumer.startConsumer();
            }
        }).start();

        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while(true){
            String nextLine = sc.nextLine();
            if(nextLine == null || nextLine.equals("")){
                continue;
            }
            nextLine = nextLine.trim();
            MqQueue.send(nextLine);
        }



    }

}
