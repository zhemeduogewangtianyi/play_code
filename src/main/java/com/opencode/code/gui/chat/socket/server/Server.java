package com.opencode.code.gui.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(8086);
            while(true){
                Socket socket = this.serverSocket.accept();
                new ServerWriter(socket,socket.getInetAddress().getHostAddress() + " 连接成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
