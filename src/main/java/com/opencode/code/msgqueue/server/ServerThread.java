package com.opencode.code.msgqueue.server;

import com.opencode.code.msgqueue.register.YellowDuckServerRegister;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class ServerThread extends Thread {

    private final Socket socket;


    private volatile boolean off = true;

    public ServerThread(String name, Socket socket) {
        super(name);
        this.socket = socket;
    }

    public boolean shutdown() {
        off = false;
        return true;
    }

    public boolean send(String msg){

        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(msg);
            bw.write("\n");
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @Override
    public void run() {

        InputStream is = null;

        BufferedReader br = null;

        OutputStream os = null;

        BufferedWriter bw = null;

        while (off) {

            try {

                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String data;
                while ((data = br.readLine()) != null) {

                    System.out.println("Server : " + this.getName() + " -> " + data);
                    YellowDuckServerRegister.send(this.getName() , data);

                }

                Thread.sleep(ThreadLocalRandom.current().nextInt(100) + 1);

            } catch (Exception e) {
                System.out.println(this.getName() + " 已注销！");
                off = false;
                YellowDuckServerRegister.unregister(this.getName());
            }finally {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        YellowDuckServerRegister.send(this.getName() , getName() + " 被注销！");

        try {
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(os != null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(is != null){
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
