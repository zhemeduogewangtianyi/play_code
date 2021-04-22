package com.opencode.code.msgqueue;

import com.alibaba.fastjson.JSON;
import com.opencode.code.msgqueue.client.YellowDuckClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final YellowDuckClient[] wty = {null};
        final YellowDuckClient[] wty1 = {null};

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    wty[0] = new YellowDuckClient("127.0.0.1", 8888, "wty");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                wty[0].start();

                try {
                    wty1[0] = new YellowDuckClient("30.248.139.11", 8888, "WTY");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                wty1[0].start();
            }
        }).start();


        Scanner scanner = new Scanner(System.in);
        while(true){

            String next = scanner.nextLine();
            Map<String,Object> map = new HashMap<>();
            map.put("success" , true);
            map.put("code" , 200);
            Map<String,Object> data = new HashMap<>();
            data.put("command",next);
            data.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            data.put("name","WTY");
            data.put("ip",wty1[0].getIp());
            map.put("data",data);
            String msg = JSON.toJSONString(map);
            boolean send = wty1[0].send(msg);

            data.put("ip",wty[0].getIp());
            String msg1 = JSON.toJSONString(map);
            boolean send1 = wty[0].send(msg1);

        }

    }

}
