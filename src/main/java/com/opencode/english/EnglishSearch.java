package com.opencode.english;

import com.alibaba.fastjson.JSON;
import org.apache.camel.com.github.benmanes.caffeine.cache.Cache;
import org.apache.camel.com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.camel.com.github.benmanes.caffeine.cache.RemovalListener;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class EnglishSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnglishSearch.class);

    private static final Cache<String, String> words = Caffeine
            .newBuilder()
            .maximumSize(120000)
            .expireAfterAccess(72 , TimeUnit.HOURS)
            .removalListener((RemovalListener<String, String>) (key, value, removalCause) -> {
                LOGGER.info("remove cache car heights configuration icon ! key : {} carHeightsConfigurationIconVO : {} " ,key , JSON.toJSONString(value));
            })
            .build();

    private static final Cache<String, Set<String>> index = Caffeine
            .newBuilder()
            .maximumSize(2000000)
            .expireAfterAccess(72 , TimeUnit.HOURS)
            .removalListener((RemovalListener<String, Set<String>>) (key, value, removalCause) -> {
                LOGGER.info("remove cache car heights configuration icon ! key : {} carHeightsConfigurationIconVO : {} " ,key , JSON.toJSONString(value));
            })
            .build();

    public static void main(String[] args) throws Exception {

        loadData();

        Scanner sc = new Scanner(System.in);

        System.out.print("please enter > ");

        String data;
        while((data = sc.nextLine()) != null){

            Set<String> ifPresent1 = index.getIfPresent(data.trim());
            if(CollectionUtils.isEmpty(ifPresent1)){
                Runtime.getRuntime().exec("say not found words ~");
                System.out.println("not found words ~");
                System.out.print("please enter > ");
                continue;
            }
            Map<@NonNull String, @NonNull String> allPresent = words.getAllPresent(ifPresent1);

            if(CollectionUtils.isEmpty(allPresent)){
                Runtime.getRuntime().exec("say not found words ~");
                System.out.println("not found words ~");
                System.out.print("please enter > ");
                continue;
            }

            Map<String,String> readMap = new HashMap<>();
            for(Iterator<Map.Entry<String, String>> car = allPresent.entrySet().iterator(); car.hasNext() ; ){
                Map.Entry<String, String> next = car.next();
                String value = next.getValue();
//                System.out.println(value);
                String os = System.getProperty("os.name").toLowerCase();
                if(os.indexOf("mac") >= 0 && os.indexOf("os") > 0){
                    String[] split = value.split("\t");
                    readMap.put(split[1] , split[1] + "," + split[2]);
                }
            }

            String text = readMap.get(data);

            if(StringUtils.isNotBlank(text)){
                Runtime.getRuntime().exec("say " + text);
                System.out.println(text);
            }else{
                Runtime.getRuntime().exec("say not found words ~");
                System.out.println("not found words ~");
            }

            System.out.print("please enter > ");

        }

    }

    private static void loadData() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("103976个英语单词库.txt");
        File file = classPathResource.getFile();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

        int bytesRead = channel.read(buffer);

        ByteBuffer stringBuffer = ByteBuffer.allocate(20);

        while (bytesRead != -1) {

            LOGGER.info("读取字节数：" + bytesRead);
            //之前是写buffer，现在要读buffer

            // 切换模式，写->读
            buffer.flip();

            while (buffer.hasRemaining()) {

                byte b = buffer.get();

                // 换行或回车
                if (b == 10 || b == 13) {

                    stringBuffer.flip();

                    // 这里就是一个行
                    final String line = StandardCharsets.UTF_8.decode(stringBuffer).toString();

                    // 解码已经读到的一行所对应的字节
//                    System.out.println(line + "----------");

                    buildIndex(line);

                    stringBuffer.clear();

                } else {

                    if (stringBuffer.hasRemaining()){
                        stringBuffer.put(b);
                    } else {
                        // 空间不够扩容
                        stringBuffer = reAllocate(stringBuffer);
                        stringBuffer.put(b);
                    }

                }

            }

            //清空,position位置为0，limit=capacity
            buffer.clear();

            //继续往buffer中写
            bytesRead = channel.read(buffer);
        }

        randomAccessFile.close();
    }

    private static void buildIndex(String line){

        String key = DigestUtils.md5Hex(line);
        words.get(key, new Function<String, String>() {
            @Override
            public String apply(String s) {
                return line;
            }
        });

        String[] split = line.split("\t");

        for(int i = 1 ; i < split.length ; i++) {

            for(int j = 0 ; j < split[i].length() ; j++){

                String wordsKey = split[i].substring(j);

                if(StringUtils.isEmpty(wordsKey)) {
                    continue;
                }

                Set<String> set = index.get(wordsKey, new Function<String, Set<String>>() {
                    @Override
                    public Set<String> apply(String s) {
                        Set<String> set = new HashSet<>();
                        set.add(key);
                        return set;
                    }
                });

                if(!CollectionUtils.isEmpty(set)){
                    set.add(key);
                }


            }

        }


    }

    private static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
        final int capacity = stringBuffer.capacity();
        byte[] newBuffer = new byte[capacity * 2];
        System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
        return (ByteBuffer) ByteBuffer.wrap(newBuffer).position(capacity);
    }

}
