package com.opencode.mockmq.socket.thread;

import com.alibaba.fastjson.JSONObject;
import com.opencode.mockmq.socket.msg.MqMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketThread extends Thread{

    private Socket socket;

    private PrintWriter pw;

    private BufferedReader br;

    private boolean run = true;

    public SocketThread(String name,Socket socket) throws IOException {
        super(name);
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        pw = new PrintWriter(socket.getOutputStream() , true);

    }

    @Override
    public void run() {
        while(run){
            try {
                String s = br.readLine();
                MqMessage mqMessage = JSONObject.parseObject(s, MqMessage.class);
                if(mqMessage.getType() == 0){
                    //读取
                    Integer valueOf = Integer.valueOf(mqMessage.getContent());

                }else{
                    //写入

                }
                Thread.sleep(50);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
