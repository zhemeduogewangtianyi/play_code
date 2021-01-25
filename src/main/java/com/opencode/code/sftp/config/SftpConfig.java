package com.opencode.code.sftp.config;

public class SftpConfig {

    private final Long id;

    private boolean runState = false;

    private Long openTime;

    private final Long activeTime;

    private final String host;

    private final int port;

    private final String username;

    private final String password;

    private final String dir;

    public SftpConfig(Long id,Long openTime, Long activeTime, String host, int port, String username, String password,String dir) {

        this.id = id;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.dir = dir;

        this.openTime = openTime;
        this.activeTime = activeTime;
    }

    public Long getId() {
        return id;
    }

    public boolean getRunState() {
        return runState;
    }

    public void setRunState(boolean runState) {
        this.runState = runState;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime){
        this.openTime = openTime;
    }

    public Long getActiveTime() {
        return activeTime;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDir() {
        return dir;
    }
}
