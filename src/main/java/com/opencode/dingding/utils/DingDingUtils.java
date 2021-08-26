package com.opencode.dingding.utils;

import com.google.common.collect.Lists;
import com.opencode.weixin.utils.DateUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DingDingUtils {

    public static String mackMid() {
        Character[] chars = {'a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Character> arrayList = new ArrayList<>(Arrays.asList(chars));
        Collections.shuffle(arrayList);
        List<List<Character>> partition = Lists.partition(arrayList, 8);
        List<Character> characters = partition.get(0);
        StringBuilder buff = new StringBuilder();
        characters.forEach(buff::append);
        buff.append(" ").append("0");
        String mid = buff.toString();
        return mid;

    }

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
