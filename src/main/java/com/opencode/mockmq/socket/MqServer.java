package com.opencode.mockmq.socket;

import com.opencode.mockmq.socket.thread.SocketThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MqServer {

    private static final Map<String, SocketThread> map = new HashMap<>();

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(8866);
        while(true){
            Socket socket = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = br.readLine();
            System.out.println(s);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("22222");
            pw.close();
            br.close();
        }

    }

}
