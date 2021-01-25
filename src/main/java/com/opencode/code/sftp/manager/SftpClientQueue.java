package com.opencode.code.sftp.manager;

import com.jcraft.jsch.*;
import com.opencode.code.sftp.config.SftpConfig;
import com.opencode.code.sftp.sftptask.SftpClientTask;
import com.opencode.code.sftp.sftptask.SftpTaskProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SftpClientQueue {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    private AtomicBoolean isLock = new AtomicBoolean(false);

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpClientQueue.class);

    private final Integer poolSize = 10;

    private final ArrayBlockingQueue<SftpClientTask> SFTP_POOL = new ArrayBlockingQueue<>(poolSize);

    private final SftpConfig sftpConfig;

    public SftpClientQueue(SftpConfig sftpConfig) {
        this.sftpConfig = sftpConfig;
    }

    public boolean upload(String fileName, InputStream is) throws Exception {

        if(SFTP_POOL.size() == 0){
            reentrantLock.lock();
            isLock.getAndSet(true);
            condition.await();
            reentrantLock.unlock();
            return upload(fileName,is);

        }

        SftpClientTask peek = SFTP_POOL.poll();
        ChannelSftp channelSftp = peek.getChannelSftp();
        sftpConfig.setRunState(true);

        try {
            String home = channelSftp.getHome();
            channelSftp.put(is, fileName,new SftpTaskProgressMonitor(is.available()));
            LOGGER.info(channelSftp + " " + home + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } catch (SftpException e) {
            throw new RuntimeException(e.getMessage() + " fileName : " + fileName);
        }finally {
            if(is != null){
                is.close();
            }
            if(peek.getChannelSftp().isConnected()){
                SFTP_POOL.put(peek);
            }
            if(isLock.get()){
                reentrantLock.lock();
                condition.signalAll();
                isLock.getAndSet(false);
                reentrantLock.unlock();
            }
        }

        return true;
    }

    public synchronized boolean addOneClient(SftpConfig sftpConfig){

        try {
            while(SFTP_POOL.size() != poolSize){
                SftpClientTask sftpClientThread = new SftpClientTask(1L, sftpConfig);
                sftpClientThread.start();
                SFTP_POOL.put(sftpClientThread);
            }
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public synchronized boolean removeOneClient(SftpClientTask sftpClientThread){
        if(!CollectionUtils.isEmpty(SFTP_POOL)){

            ChannelSftp channelSftp = sftpClientThread.getChannelSftp();
            Session session = null;
            try {
                session = channelSftp.getSession();
            } catch (JSchException e) {
                e.printStackTrace();
            }

            if(channelSftp.isConnected()){
                channelSftp.disconnect();
            }
            if(session != null && session.isConnected()){
                session.disconnect();
            }

            boolean remove = SFTP_POOL.remove(sftpClientThread);
            if(SFTP_POOL.size() < poolSize){
                this.addOneClient(sftpConfig);
            }
            return remove;
        }
        return false;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public Integer getQueueSize() {
        return SFTP_POOL.size();
    }
}
