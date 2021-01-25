package com.opencode.code.sftp;

import com.jcraft.jsch.*;
import com.opencode.code.sftp.config.SftpConfig;
import com.opencode.code.sftp.manager.SftpClientRegisterCenter;
import com.opencode.code.sftp.util.FileSizeConverUtil;

import java.io.*;
import java.util.concurrent.*;

public class SftpMain {


    private static String username = "sftpuser";
    private static String password = "sftpuser";
    private static String host = "11.164.25.64";
    private static int port = 22;
    private static String dir = "ccc";

    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(32, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
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


    public static void main(String[] args) throws FileNotFoundException {
        new SftpMain().test1();
    }

    void test1() throws FileNotFoundException {
        long openTime = System.currentTimeMillis();
        long activeTime = 60000;
        SftpConfig sftpConfig = new SftpConfig(1L,openTime,activeTime,host,port,username,password,dir);
        SftpConfig sftpConfig1 = new SftpConfig(2L,openTime,activeTime,host,port,username,password,"cccc");
        boolean b = SftpClientRegisterCenter.addClient(sftpConfig);
        boolean b1 = SftpClientRegisterCenter.addClient(sftpConfig1);

        File file = new File("C:\\Users\\王添一\\Desktop\\ccc");
        File[] files = file.listFiles();
        for(File f : files){
            InputStream fis = new FileInputStream(f);

//            try {
//                System.out.println(FileSizeConverUtil.fileSizeConver(fis.available()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    execute(1L,f.getName(),fis);
                }
            });

            InputStream fis1 = new FileInputStream(f);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    execute(2L,f.getName(),fis1);
                }
            });
        }
    }

    public void execute(Long id,String fileName , InputStream fis){
        try{

            boolean upload = SftpClientRegisterCenter.upload(id,fileName,fis);
            Thread.sleep(500);
        }catch(Exception e){
                e.printStackTrace();
        }
    }

    public static void test(){
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "password");
            session.connect(5000);

            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect(5000);

            sftpChannel.cd(sftpChannel.getHome());

            sftpChannel.cd("c");

            sftpChannel.disconnect();
            session.disconnect();


        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

}
