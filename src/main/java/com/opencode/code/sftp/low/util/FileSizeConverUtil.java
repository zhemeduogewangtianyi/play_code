package com.opencode.code.sftp.low.util;

import java.text.DecimalFormat;

/**
 * 文件大小计算
 * @author  WTY
 * @date    2020/7/29 9:51
 */
public class FileSizeConverUtil {

    public static String fileSizeConver(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString ;
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824){
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else{
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

}
