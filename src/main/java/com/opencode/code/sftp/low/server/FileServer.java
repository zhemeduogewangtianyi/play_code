package com.opencode.code.sftp.low.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class FileServer {

    private static final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);

    private static ThreadPoolExecutor POOL = new ThreadPoolExecutor(5, 10,
            60L, TimeUnit.SECONDS, queue
            , new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) {

        ServerSocket serverSocket;

        try{

            serverSocket = new ServerSocket(8888);

            while(true){
                Socket socket = serverSocket.accept();

                POOL.execute(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                            String data;
                            while((data = br.readLine()) != null){
                                System.out.println(data);
                            }
                            PrintWriter pw = new PrintWriter(socket.getOutputStream());
                            pw.println("OK");
                            br.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
