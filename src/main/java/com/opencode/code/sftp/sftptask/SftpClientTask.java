package com.opencode.code.sftp.sftptask;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.opencode.code.sftp.config.SftpConfig;
import com.opencode.code.sftp.manager.SftpClientRegisterCenter;

import java.util.concurrent.ThreadLocalRandom;

public class SftpClientTask extends Thread{

    private SftpConfig sftpConfig;

    private ChannelSftp channelSftp;

    private boolean runnable = true;

    public SftpClientTask(Long id, SftpConfig sftpConfig) {
        super(id.toString());
        this.sftpConfig = sftpConfig;

        createChannelSftp();

    }

    void createChannelSftp(){

        String username = this.sftpConfig.getUsername();
        String password = this.sftpConfig.getPassword();
        String host = this.sftpConfig.getHost();
        int port = this.sftpConfig.getPort();

        try{
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "password");
            session.connect(5000);
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
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
                    SftpClientRegisterCenter.addClient(sftpConfig);
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

    }
}
