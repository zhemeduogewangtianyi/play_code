package com.opencode.code.msgqueue.server;


import com.opencode.code.msgqueue.register.YellowDuckServerRegister;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class YellowDuckServer extends ServerSocket implements Runnable {


    public YellowDuckServer(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {

        while(true){

            try{

                Socket socket = this.accept();

                String hostName = socket.getInetAddress().getHostName();
                YellowDuckServerRegister.register(hostName,new ServerThread(hostName,socket));

                Thread.sleep(ThreadLocalRandom.current().nextInt(100) + 1);

            }catch(Exception e){
                System.out.println("111");
                continue;
            }

        }

    }

    public static void main(String[] args) throws IOException {
        new Thread(new YellowDuckServer(8888)).start();
    }

}
