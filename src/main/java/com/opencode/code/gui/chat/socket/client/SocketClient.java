package com.opencode.code.gui.chat.socket.client;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {

    private Socket socket;

    public SocketClient(String ip , int port) {
        try {
            socket = new Socket(ip,port);
            new ClientReader(socket);
            new ClientWriter(socket,"啊啊啊");

//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            System.out.println(br.readLine());
//
//            PrintWriter pw = new PrintWriter(socket.getOutputStream());
//            pw.println("111");
//            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
