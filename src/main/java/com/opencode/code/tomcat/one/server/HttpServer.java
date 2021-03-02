package com.opencode.code.tomcat.one.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    /**
     * 工作目录
     * WEB服务器可以处理对指定目录中的静态资源请求，该目录包括由静态变量 final WEB_ROOT 指明的目录及其所有子目录
     * */
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    /** 关闭命令 */
    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    /** 接受关闭命令 */
    private boolean shutdown = false;

    public static void main(String[] args) {

        HttpServer server = new HttpServer();
        server.await();

    }

    public void await(){
        ServerSocket serverSocket = null;
        int port = 8082;

        try{
            serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        while(!shutdown){
            Socket socket = null;
            InputStream is = null;
            OutputStream os = null;

            try{

                //创建套接字
                socket = serverSocket.accept();

                //输入流
                is = socket.getInputStream();
                //输出流
                os = socket.getOutputStream();

                //解析原始的 HTTP 请求数据
                Request request = new Request(is);
                request.parse();

                //响应结果
                Response response = new Response(os);
                response.setRequest(request);
                response.sendStaticResource();

                //关闭套接字
                socket.close();

                //检查 URI 是不是一个关闭请求
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }

    }


}
