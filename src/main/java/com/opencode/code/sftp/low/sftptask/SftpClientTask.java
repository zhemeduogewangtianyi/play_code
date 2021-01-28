package com.opencode.code.sftp.low.sftptask;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.opencode.code.sftp.low.config.SftpConfig;
import com.opencode.code.sftp.low.manager.SftpClientQueue;
import com.opencode.code.sftp.low.manager.SftpClientRegisterCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class SftpClientTask extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpClientTask.class);

    private SftpConfig sftpConfig;

    private final SftpClientQueue sftpClientQueue;

    private ChannelSftp channelSftp;

    private Session session;

    private boolean runnable = true;

    private final Long version;

    public SftpClientTask(SftpConfig sftpConfig,SftpClientQueue sftpClientQueue) {
        super(sftpConfig.getId().toString());
        this.version = sftpConfig.getVersion();
        this.sftpConfig = sftpConfig;
        this.sftpClientQueue = sftpClientQueue;

        createChannelSftp();

    }

    private void createChannelSftp(){

        String username = this.sftpConfig.getUsername();
        String password = this.sftpConfig.getPassword();
        String host = this.sftpConfig.getHost();
        int port = this.sftpConfig.getPort();

        try{
            JSch jSch = new JSch();
            this.session = jSch.getSession(username, host, port);
            this.session.setPassword(password);
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.setConfig("PreferredAuthentications", "password");
            this.session.connect(5000);
            ChannelSftp channelSftp = (ChannelSftp) this.session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(channelSftp.getHome());
            boolean dirExist = isDirExist(channelSftp, sftpConfig.getDir());
            if(dirExist){
                channelSftp.cd(sftpConfig.getDir());
            }else{
                channelSftp.mkdir(sftpConfig.getDir());
                channelSftp.cd(sftpConfig.getDir());
            }
            this.channelSftp = channelSftp;
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public ChannelSftp getChannelSftp() {
        this.sftpConfig.setOpenTime(System.currentTimeMillis());
        return channelSftp;
    }

    public SftpConfig getSftpConfig() {
        return sftpConfig;
    }

    public Long getVersion() {
        return version;
    }

    public boolean stopTask(SftpConfig sftpConfig){
        this.runnable = false;
        this.sftpConfig = sftpConfig;
        return true;
    }

    public boolean stopTask(){
        this.runnable = false;
        return true;
    }

    @Override
    public void run() {

        while(runnable){

            try {

                Long activeTime = this.sftpConfig.getActiveTime();
                boolean ttl = false;
                boolean closed = false;
                if(activeTime > 0){
                    ttl = System.currentTimeMillis() - this.sftpConfig.getOpenTime() > activeTime;
                    closed = this.channelSftp.isClosed();
                }

                if((closed || ttl) && this.sftpConfig.getRunState()){
                    SftpClientRegisterCenter.removeOneClient(Long.valueOf(this.getName()),this);
                    this.runnable = false;
                }else {
                    SftpConfig sftpConfig = this.getSftpConfig();
                    sftpConfig.setOpenTime(System.currentTimeMillis());
                }

                Thread.sleep(ThreadLocalRandom.current().nextInt(100) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info(channelSftp + " update : " + this.sftpConfig.getVersion() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SftpClientRegisterCenter.removeOneClient(sftpConfig.getId(),this);
        SftpClientRegisterCenter.addClient(this.sftpClientQueue.getSftpConfig());

    }

}
