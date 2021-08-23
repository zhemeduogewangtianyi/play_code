package com.opencode.dingding.utils;

import com.opencode.weixin.utils.DateUtils;

import java.io.*;

public class DingDingUtils {

    public static File saveFile(InputStream inputStream, String dirPath, String id) {
        return saveFileByDay(inputStream, dirPath, id, false);
    }

    public static File saveFileByDay(InputStream inputStream, String dirPath, String id, boolean byDay) {
        OutputStream outputStream = null;
        try {
            if (byDay) {
                dirPath = dirPath + "/" + DateUtils.getDateString();
            }
            File dir = new File(dirPath);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            File path = new File(dir, id);
            if (path.exists()) {
                path.delete();
            }
            outputStream = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            return path;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }

}
