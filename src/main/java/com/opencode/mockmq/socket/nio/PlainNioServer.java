package com.opencode.mockmq.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * nio
 * */
public class PlainNioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8866));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector , SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi".getBytes(StandardCharsets.UTF_8));
        while(true){

            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for(Iterator<SelectionKey> car = selectionKeys.iterator(); car.hasNext() ; ){
                SelectionKey selectionKey = car.next();
                car.remove();

                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector , SelectionKey.OP_WRITE | SelectionKey.OP_READ , msg.duplicate());

                    System.out.println("Accepted connection from " + client);

                    client.write(msg);

                }

                if(selectionKey.isWritable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    while(buffer.hasRemaining()){
                        if(client.write(buffer) == 0){
                            break;
                        }
                        System.out.println(client.read(buffer));
                    }
                    client.write(msg);
                    client.close();
                }

            }

        }


    }

}
