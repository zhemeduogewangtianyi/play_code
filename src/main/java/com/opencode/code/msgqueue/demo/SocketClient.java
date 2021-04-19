package com.opencode.code.msgqueue.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        // 1.建立Socket，指定IP和端口
        Socket socket = new Socket("localhost", 6666);
        // 2.获取输出流
        OutputStream outputStream = socket.getOutputStream();
        // 3.向服务端发送消息
        String message = "Hello, Server!";
        outputStream.write(message.getBytes("UTF-8"));
        //通过shutdownOutput高速服务器已经发送完数据,后续只能接受数据
        socket.shutdownOutput();
        // 4.获取输入流，接收服务端的消息
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式,发送方和接收方一定要统一,建议使用UTF-8
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        System.out.println("get message from server: " + sb);
        // 5.关闭流，Socket
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
