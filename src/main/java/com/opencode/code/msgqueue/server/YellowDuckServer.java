package com.opencode.code.msgqueue.server;


import org.springframework.util.Base64Utils;

import java.io.*;
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

                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String data;
                while((data = br.readLine()) != null){
                    System.out.println(data);

                    OutputStream os = socket.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    String hi = "hello world !";
                    bw.write(hi);
                    bw.write("\n");
                    bw.flush();

                }


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
