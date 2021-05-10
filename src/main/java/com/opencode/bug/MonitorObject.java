package com.opencode.bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.code.listeners.change2.mock.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MonitorObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorObject.class);

    public static void main(String[] args) {

        Redis redis = new Redis();

        for(int i = 0 ; i < 1000 ; i++){
            Map<String,Object> map = new HashMap<>();
            map.put("fileName",i);
            map.put("UUID", UUID.randomUUID().toString());
            map.put("requetsTime",System.currentTimeMillis());
            String json = JSON.toJSONString(map);
            redis.push("W" , json);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String,Object> temp = new HashMap<>();
        temp.put("a","A");

        while(true){

            String json = redis.poll("W").toString();

            JSONObject jsonObject = JSONObject.parseObject(json);
            jsonObject.put("temp",temp);


            if(json == null){
                return;
            }

            test(jsonObject);

        }

    }

    private static void test(JSONObject object) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = object.get("temp");
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(30));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info(o.hashCode() +"");
            }
        }).start();

    }

}
