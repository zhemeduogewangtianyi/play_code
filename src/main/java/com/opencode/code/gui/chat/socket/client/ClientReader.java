package com.opencode.code.gui.chat.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class ClientReader extends Thread {

    private Socket socket;

    public ClientReader(Socket socket) {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String data;
            while ((data = br.readLine()) != null){
                System.out.println(data);
            }

        } catch (IOException e) {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }finally {

//            try {
//                if(br != null){
//                    br.close();
//                }
//                if(isr != null){
//                    isr.close();
//                }
//                if(is != null){
//                    is.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
