package com.opencode.code.gui.chat.socket.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWriter extends Thread {

    private Socket socket;

    private String message;

    public ServerWriter(Socket socket,String message) {
        this.socket = socket;
        this.message = message;
        start();
    }

    @Override
    public void run() {
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.println(message);
            pw.flush();
            new ServerReader(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//
//            if(pw != null){
//                pw.flush();
//                pw.close();
//            }
//            try {
//                if(os != null){
//                    os.flush();
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
