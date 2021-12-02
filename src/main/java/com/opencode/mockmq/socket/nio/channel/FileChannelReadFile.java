package com.opencode.mockmq.socket.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelReadFile {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/wty/Desktop/地理库.md" , "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(8096);

        int len;
        while((len = fileChannel.read(allocate)) != -1){

            System.out.println("Read " + len);

            allocate.flip();

            while(allocate.hasRemaining()){
                System.out.println((char)allocate.get());
            }

            allocate.compact();

        }

        fileChannel.close();

    }

}
