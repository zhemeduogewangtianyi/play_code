package com.opencode.code.sftp.sftptask;

import com.jcraft.jsch.SftpProgressMonitor;
import com.opencode.code.sftp.util.FileSizeConverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SftpTaskProgressMonitor implements SftpProgressMonitor {

    private static Logger LOGGER = LoggerFactory.getLogger(SftpTaskProgressMonitor.class);

    private long maxCount = 0;

    private long uploadCount = 0;

    public long startTime = 0L;

    private String fileName;

    public SftpTaskProgressMonitor(long maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        this.startTime = System.currentTimeMillis();
        this.fileName = dest;
        LOGGER.info("开始上传文件：" + src + "至远程：" + dest + "文件总大小:" + FileSizeConverUtil.fileSizeConver(maxCount));
    }

    @Override
    public boolean count(long count) {
        uploadCount += count;
        if(uploadCount == maxCount){
            LOGGER.info("传输完成！用时：" + (System.currentTimeMillis() - startTime) / 1000 + "s");
            return false;
        }
        return true;
    }

    @Override
    public void end() {
        LOGGER.info(fileName + " end !");
    }
}
