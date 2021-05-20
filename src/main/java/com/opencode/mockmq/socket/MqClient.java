package com.opencode.mockmq.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MqClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1",8888);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println("111111");
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = br.readLine();
        System.out.println(s);

    }

}
