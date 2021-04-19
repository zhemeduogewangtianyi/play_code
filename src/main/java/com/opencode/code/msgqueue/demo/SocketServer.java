package com.opencode.code.msgqueue.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        // 1.创建Socket服务端，监听指定端口
        ServerSocket serverSocket = new ServerSocket(6666);
        // 2.等待客户端连接
        System.out.println("server将一直等待连接的到来");
        Socket socket = serverSocket.accept();
        // 3.建立好连接之后,从Socket获取输入流,并建立缓冲区进行读取
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
        while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式,发送方和接收方一定要统一,建议使用UTF-8
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        System.out.println("get message from client: " + sb);
        // 4.从Socket获取输出流，并向客户端发送消息
        OutputStream outputStream = socket.getOutputStream();
        String message = "Hello Client,I get the message.";
        outputStream.write(message.getBytes("UTF-8"));
        // 5.关闭流、Socket
        inputStream.close();
        outputStream.close();
        socket.close();
        serverSocket.close();
    }
}
