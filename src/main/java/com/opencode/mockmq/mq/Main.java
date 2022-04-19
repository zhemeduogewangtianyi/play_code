package com.opencode.mockmq.mq;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Main {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(8888));
        socketChannel.configureBlocking(false);

        while(true){
            SocketChannel accept = socketChannel.accept();

            if(accept != null){
                ByteBuffer readBuff = ByteBuffer.allocate(10384);
                accept.read(readBuff);

                System.out.println(readBuff.toString());
            }

        }

    }

}
