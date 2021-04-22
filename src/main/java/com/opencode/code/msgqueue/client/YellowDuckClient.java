package com.opencode.code.msgqueue.client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class YellowDuckClient extends Thread {

    private final Socket socket;

    private final String ip;

    private final InputStream is;
    private final OutputStream os;

    private boolean off = true;

    public YellowDuckClient(String host,int port,String name) throws IOException {
        super(name);
        this.ip = host;
        socket = new Socket(host,port);
        is = socket.getInputStream();
        os = socket.getOutputStream();

    }

    public String getIp(){
        return ip;
    }

    public boolean shutdown(){
        this.off = false;
        return true;
    }


    public boolean send(String msg){

        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(msg);
            bw.write("\n");
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @Override
    public void run() {

        this.send(getName() + " 已注册！");

        while(off){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                String data ;
                while((data = br.readLine()) != null){
                    System.out.println("Client : " + this.getName() +  " -> " + data);
                }
            }  catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        if(os != null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(is != null){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
