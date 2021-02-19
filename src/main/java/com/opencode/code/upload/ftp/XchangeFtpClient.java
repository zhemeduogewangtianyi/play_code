package com.opencode.code.upload.ftp;

import com.alibaba.fastjson.JSON;
import com.opencode.code.upload.config.XchangeDeliveryConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

public class XchangeFtpClient extends FTPClient implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(XchangeFtpClientFactory.class);

    private static final Logger FTP_LOGGER = LoggerFactory.getLogger(XchangeFtpClientFactory.class);

    private final XchangeDeliveryConfig config;

    private Long lastCheckTime;

    private volatile boolean off = true;

    public XchangeFtpClient(XchangeDeliveryConfig config) {
        this.config = config;
        this.lastCheckTime = System.currentTimeMillis();
    }

    public XchangeFtpClient getClient() {

        this.setControlEncoding(config.getEncoding());
        this.setConnectTimeout(config.getConnectTimeout());
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        this.configure(conf);
        String initialPath;
        try {
            LOGGER.info("try to connect to ftp server, config={}", JSON.toJSONString(config));
            if(!this.isConnected()){
                this.connect(config.getHost(), config.getPort());
            }
            int replyCode = this.getReplyCode();
            LOGGER.info("connect response code={}", replyCode);
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                this.disconnect();
                String errorInfo = String.format("FTPServer refused connection,ftp config=%s, replyCode:%s",
                        JSON.toJSONString(config), replyCode);
                FTP_LOGGER.error(errorInfo);
                LOGGER.error(errorInfo);
                return null;
            }

            if (!this.login(config.getUsername(), config.getPassword())) {
                String errorInfo = String.format("ftpClient login failed... ftp config=%s", JSON.toJSONString(config));
                FTP_LOGGER.error(errorInfo);
                LOGGER.error(errorInfo);
            }

            this.setBufferSize(config.getBufferSize());
            this.setFileType(config.getTransferFileType());
            if (config.isPassiveMode()) {
                this.enterLocalPassiveMode();
                LOGGER.info(config.getHost() + " enterLocalPassiveMode !");
            }else {
                this.enterLocalActiveMode();
                LOGGER.info(config.getHost() + " enterLocalActiveMode !");
            }

            //成功建立连接后获取初始路径，以后每次从连接池中取出来之前都需要回到初始路径
            initialPath = this.printWorkingDirectory();
            String changeDir = config.getChangeDir();
            if (StringUtils.isNotBlank(changeDir) && !"N/A".equalsIgnoreCase(changeDir)) {
                try {
                    // 判断是否change工作目录成功
                    boolean res = this.changeWorkingDirectory(changeDir);
                    if (!res) {
                        // change不成功，则创建目录，同时设置目录为工作目录
                        boolean isAbsolutePath = changeDir.startsWith("/");
                        String[] dirs = changeDir.split("/");
                        if(isAbsolutePath){
                            dirs[0] = "/" + dirs[0];
                        }
                        for (String dir : dirs) {
                            if (!this.changeWorkingDirectory(dir)) {
                                if(this.makeDirectory(dir)){
                                    if(!this.changeWorkingDirectory(dir)){
                                        throw new IllegalArgumentException("[ERROR] create ftp server directory failed,path=" + changeDir);
                                    }
                                }else{
                                    throw new IllegalArgumentException("[ERROR] create ftp server directory failed,path=" + changeDir);
                                }
                            }
                        }
                    }
                    //如果有用户自定义的路径，则返回该路径
                    initialPath = this.printWorkingDirectory();
                } catch (Exception e) {
                    String errorInfo = String.format("change  dworkingir error, config=%s", JSON.toJSONString(config));
                    FTP_LOGGER.error(errorInfo, e);
                    LOGGER.error(errorInfo, e);
                    return null;
                }
            }
            LOGGER.info("ftp init path:{}",initialPath);
        } catch (IOException e) {
            String errorInfo = String.format("create ftp connection failed... config:[%s]", JSON.toJSONString(config));
            LOGGER.error(errorInfo, e);
            FTP_LOGGER.error(errorInfo, e);
            return null;
        }

        return this;

    }

    protected void off(){
        this.off = false;
    }

    public XchangeDeliveryConfig getConfig() {
        return config;
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
                LOGGER.info(config.getChangeDir() + " 最后报警时间 ： " + lastCheckTime + " 创建时间 ： " + config.getVersion());
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


    public boolean upload(InputStream is, String fileName) throws IOException {
        return this.storeFile(fileName, is);
    }

    public boolean download() {
        return true;
    }

    public void changeConfig(){

    }

}
