package com.opencode.mockmq.mq.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class MtyMqServer {

    public static void main(String[] args) throws IOException {


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1" , 8888));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuffer = ByteBuffer.allocate(20);


        while(true) {

            try{
                int select = selector.select(5000);
                Set<SelectionKey> keys = selector.selectedKeys();
                for(Iterator<SelectionKey> car = keys.iterator(); car.hasNext() ; ){
                    SelectionKey next = car.next();
                    car.remove();


                    if(next.isAcceptable()){

                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector , SelectionKey.OP_READ);

                    }else if(next.isReadable()){

                        SocketChannel channel = (SocketChannel) next.channel();

                        if(channel != null){

                            readBuffer.clear();
                            channel.read(readBuffer);
                            readBuffer.flip();
                            System.out.println("received : " + new String(readBuffer.array()));
                            next.interestOps(SelectionKey.OP_WRITE);

                        }

                    }else if (next.isWritable()){

//                        writeBuffer.rewind();

                        ByteBuffer writeBuffer = ByteBuffer.allocate(20);

                        writeBuffer.put(StandardCharsets.UTF_8.encode("server"));
                        writeBuffer.flip();

                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        if(!socketChannel.isConnectionPending()){
                            ByteBuffer wrap = ByteBuffer.wrap(writeBuffer.array());
                            System.out.println("out : " + new String(wrap.array()));
                            socketChannel.write(writeBuffer);
                            socketChannel.finishConnect();
                            next.interestOps(SelectionKey. OP_READ );
                        }else{
                            socketChannel.close();
                        }

                    }
                }
            }catch (Exception e){
                continue;
            }

        }

    }

}
