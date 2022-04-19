package com.opencode.mockmq.mq.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class MtyMqClient {

    public static void main(String[] args) throws IOException {

        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

        sc.configureBlocking(false);

        String data = "hello\n";
        //创建buffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(data.getBytes(StandardCharsets.UTF_8).length);
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        //给buffer写入数据
        writeBuffer.put(data.getBytes(StandardCharsets.UTF_8));
        //模式切换
        writeBuffer.flip();

//        while (true) {

            writeBuffer.rewind();
            //写入通道数据
            sc.write(writeBuffer);
            //关闭
            readBuffer.clear();
            sc.read(readBuffer);

            sc.finishConnect();

            try {
                Thread.sleep(100);
                System.out.println("received : " + new String(readBuffer.array()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//        sc.close();
//        }

    }

}
