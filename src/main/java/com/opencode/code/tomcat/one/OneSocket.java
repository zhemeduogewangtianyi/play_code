package com.opencode.code.tomcat.one;

import java.io.*;
import java.net.Socket;

public class OneSocket {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1",8080);

        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os,true);
        pw.println("GET /index.jsp HTTP/1.1");
        pw.println("Host: localhost:8080");
//        pw.println("Connection: Close");
        pw.println("Connection: keep-alive");

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer sb = new StringBuffer(8096);
        boolean loop = true;
        while(loop){
            if( br.ready() ){
                int i = 0 ;
                while( i != -1 ){
                    i = br.read();
                    sb.append((char)i);
                }
            }
            loop = false;
            Thread.currentThread().sleep(50);
        }

        System.out.println(sb.toString());

    }

}
