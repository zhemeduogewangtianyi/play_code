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

public class NioClient {

    private SocketChannel client;

    private Selector selector;

    private ByteBuffer buffer;

    public NioClient() throws IOException {

        this.selector = Selector.open();

        this.client = SocketChannel.open(new InetSocketAddress("127.0.0.1",8866));
        client.configureBlocking(false);

        client.register(selector , SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        this.buffer = ByteBuffer.allocate(25500);

        connect();
    }

    private void connect() throws IOException {

        while(true){

            if(selector.select() > 0){

                for(Iterator<SelectionKey> car = selector.selectedKeys().iterator(); car.hasNext() ; ){
                    SelectionKey selectionKey = car.next();
                    if(selectionKey.isReadable()){
                        reads();
                    }
                    if(selectionKey.isWritable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        writes(client , "一二三\n");
                    }

                    car.remove();

                }
            }

        }

    }

    private void reads() throws IOException {

        buffer.clear();
        client.read(buffer);
        buffer.flip();
        String data = new String(buffer.array(), 0, buffer.limit());
        System.out.println(data);

    }

    private void writes(SocketChannel accept , String msg) throws IOException {
        buffer.clear();
        buffer.put(msg.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        accept.write(buffer);
    }

    public static void main(String[] args) throws IOException {
        new NioClient();
    }

}
