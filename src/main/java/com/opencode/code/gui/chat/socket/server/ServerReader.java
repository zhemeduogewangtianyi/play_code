package com.opencode.code.gui.chat.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerReader extends Thread{

    private Socket socket;

    public ServerReader(Socket socket) {
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
            isr = new InputStreamReader(is,"UTF-8");
            br = new BufferedReader(isr);
            String data;
            while((data = br.readLine()) != null){
                System.out.println(data);
            }
        } catch (IOException e) {
            System.out.println(socket.getInetAddress().getHostAddress() + " 离开");
        }

    }
}
