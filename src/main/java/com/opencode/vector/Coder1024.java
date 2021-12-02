package com.opencode.vector;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author jiangjibo
 * @date 2021/10/22 10:28 上午
 * @description: TODO
 */
public class Coder1024 {

    private int cores = Runtime.getRuntime().availableProcessors();

    private Set<Integer> primeNumbers = ConcurrentHashMap.newKeySet(2 ^ 23);

    private List<List<String>> outputs = new ArrayList<>(16);

    private static String outputPath = "/data/result/output.data";
//    private static String outputPath = "/Users/jiangjibo/Downloads/output.data";

    private static String inputPath = "/data/input.data";
//    private static String inputPath = "/Users/jiangjibo/Downloads/input.data";

    private List<Element> elements = new ArrayList<>(16);

    public static void main(String[] args) throws IOException, InterruptedException {
//        long s = System.currentTimeMillis();
        Coder1024 coder = new Coder1024();
        coder.init(inputPath);
        coder.start();
//        long e = System.currentTimeMillis();
//        System.out.println("累计耗时：" + (e - s) + "毫秒");
    }

    public void init(String path) throws IOException {
        splitFile(new File(path));
        for (int i = 0; i < cores; i++) {
            outputs.add(new ArrayList<>(2 ^ 20));
        }
    }

    public void start() throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(cores);
        for (Element element : elements) {
            new Thread(() -> {
                processElement(element);
                latch.countDown();
            }).start();
        }
        latch.await();
        processAfter();
        writeResult();
//        System.out.println(getMD5Three(outputPath));
    }

    private void splitFile(File file) throws IOException {
        // 字节长度
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        // 每份长度
        int size = bytes.length / cores;

        for (int i = 0; i < cores; i++) {
            Element ele = new Element();
            ele.bytes = bytes;
            ele.start = i * size;
            ele.end = (i + 1) * size;
            ele.index = i;
            if (i == cores - 1) {
                ele.end = bytes.length;
            }
            elements.add(ele);
        }
//        is.close();
    }

    private void processElement(Element element) {
        // 字符串起始序号
        int s = -1;
        byte[] bytes = element.bytes;
        int x = bytes.length - 1;
        for (int i = element.start; i < element.end; i++) {
            // 当前字节
            byte b = bytes[i];
            if (b == 's' && s == -1) {
                s = i;
                continue;
            }
            if (b == '\n' || i == x) {
                // 第一条字符串
                if (s == -1) {
                    s = i + 1;
                } else {
                    processLine(element.index, new String(bytes, s, i == x ? i - s + 1 : i - s));
                    s = i;
                }
            }
        }
    }

    public void processLine(int index, String line) {
        boolean st = line.charAt(0) == '\n';
        int l = line.length();
        boolean end = line.charAt(l - 1) == '\n';
        if (line.startsWith(st ? "\nshuli" : "shuli")) {
            String s = line.substring(st ? 13 : 12, end ? l - 1 : l);
            int number = Integer.parseInt(s);

            if (primeNumbers.contains(number)) {
                outputs.get(index).add(s);
                return;
            }
            boolean isPN = isPrime(number);
            if (isPN) {
                primeNumbers.add(number);
                outputs.get(index).add(s);
            }
        }
    }

    public static boolean isPrime(int a) {

        boolean flag = true;

        if (a < 2) {// 素数不小于2
            return false;
        } else {

            for (int i = 2; i <= Math.sqrt(a); i++) {

                if (a % i == 0) {// 若能被整除，则说明不是素数，返回false

                    flag = false;
                    break;// 跳出循环
                }
            }
        }
        return flag;
    }

    private void processAfter() {
        for (int i = 1; i < elements.size(); i++) {
            Element e = elements.get(i);
            if (e.bytes[e.start] == 's') {
                continue;
            }
            int end = 0;
            for (int j = e.start; j < e.end; j++) {
                // 当前字节
                byte b = e.bytes[j];
                if (b == '\n' || b == e.bytes.length) {
                    end = j;
                    break;
                }
            }

            int start = 0;
            Element pre = elements.get(i - 1);
            for (int j = pre.end - 1; j >= pre.start; j--) {
                // 当前字节
                byte b = pre.bytes[j];
                if (b == '\n') {
                    start = j + 1;
                    break;
                }
            }

            String line = new String(e.bytes, start, end - start);
            processLine(i - 1, line);

        }
    }

    private void writeResult() throws IOException {
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file), 1024 * 1024);

        try {
            Iterator var3 = this.outputs.iterator();

            while (var3.hasNext()) {
                List<String> output = (List) var3.next();
                Iterator var5 = output.iterator();

                while (var5.hasNext()) {
                    String num = (String) var5.next();
                    bw.write(num);
                    bw.newLine();
                }
            }
        } finally {
            if (bw != null) {
                bw.close();
            }

        }

    }

    public static String getMD5Three(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }


    private static class Element {
        private int index;
        private byte[] bytes;
        private int start;
        private int end;
    }

}
