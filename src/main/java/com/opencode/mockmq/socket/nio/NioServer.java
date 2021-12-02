package com.opencode.mockmq.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buffer;

    public NioServer() throws IOException {

        this.selector = Selector.open();

        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.bind(new InetSocketAddress(8866));
        this.serverSocketChannel.register(selector , SelectionKey.OP_ACCEPT);
        this.buffer = ByteBuffer.allocate(25500);

        openServer();
    }

    private void openServer() throws IOException {
        while(true){
            if(selector.select() > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(Iterator<SelectionKey> car = selectionKeys.iterator() ; car.hasNext() ; ){
                    SelectionKey selectionKey = car.next();
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel accept = server.accept();

                        read(accept);

                        send(accept , "12345");
                    }
                    car.remove();
                }
            }
        }
    }

    private void read(SocketChannel accept) throws IOException {
        buffer.clear();
        accept.read(buffer);
        buffer.flip();
        System.out.println(new String(buffer.array() , 0 , buffer.limit()));
    }

    private void send(SocketChannel accept , String msg) throws IOException {
        buffer.clear();
        buffer.put(msg.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        accept.write(buffer);
    }

    public static void main(String[] args) throws IOException {
        new NioServer();
    }

}
