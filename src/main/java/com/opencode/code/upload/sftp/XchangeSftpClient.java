package com.opencode.code.upload.sftp;

import com.jcraft.jsch.*;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author    WTY
 * @date    2021/2/18 15:06
 */
public class XchangeSftpClient implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(XchangeSftpClient.class);

    /** 配置 */
    @Getter
    private XchangeDeliveryConfig config;

    /** session */
    @Getter
    private final Session session;

    /** channel */
    @Getter
    private final ChannelSftp channel;

    /** 最后检查时间 */
    private Long lastCheckTime;

    /** 线程开关 */
    private volatile boolean off = true;

    public XchangeSftpClient(XchangeDeliveryConfig config) throws Exception {
        this.config = config;
        JSch jSch = new JSch();
        String username = config.getUsername();
        String password = config.getPassword();
        String host = config.getHost();
        Integer port = config.getPort();
        Integer connectTimeout = config.getConnectTimeout();

        session = jSch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "password");
        session.connect();
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect(connectTimeout);

        if (!isDirExist(channel, config.getChangeDir())) {
            channel.mkdir(config.getChangeDir());
        }

        channel.cd(config.getChangeDir());

        this.channel = channel;

        this.lastCheckTime = System.currentTimeMillis();
    }

    public static boolean isDirExist(ChannelSftp sftp, String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 关闭线程
     */
    public boolean off() {
        try{
            if(channel.isConnected()){
                channel.disconnect();
            }
            if(session.isConnected()){
                session.disconnect();
            }
            this.off = false;
            return !this.off;
        }catch(Exception e){
            LOGGER.error("close sftp channel and session and thread failed !");
            return false;
        }
    }

    @Override
    public void run() {
        while(off){
            Long checkFrequency = config.getCheckFrequency();
            if(checkFrequency == null){
                continue;
            }
            long frequency = checkFrequency * 60 * 1000;
            long currentTimeMillis = System.currentTimeMillis();
            if(currentTimeMillis - lastCheckTime >= frequency){
                freshLastCheckTime();
                LOGGER.info("SFTP : " + config.getChangeDir() + " 最后报警时间 ： " + lastCheckTime + " 创建时间 ： " + config.getVersion());
                if(!ping()){
                    //报警
                    System.out.println("报警");
                }
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void freshLastCheckTime(){
        this.lastCheckTime = System.currentTimeMillis();
    }

    public boolean ping() {

        Socket socket = null;
        try{
            String addr = this.config.getHost();
            int port = this.config.getPort();
            URI uri = URI.create(addr);
            URI res = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), null, null);
            String host = res.getHost();
            if(StringUtils.isNotBlank(host)){
                addr = res.getHost();
            }

            socket = new Socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(addr, port);
            socket.connect(inetSocketAddress,3000);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean upload(InputStream is, String fileName) throws SftpException {
        channel.put(is, fileName);
        return true;
    }

}
