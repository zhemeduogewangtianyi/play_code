package com.opencode.code.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping(value = "redis")
@RestController
public class RedisConnectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConnectionController.class);


    @RequestMapping(value = "/operator")
    public Object redisView(
            @RequestParam String host,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Integer port,
            @RequestParam String command
    ){
        if(port == null){
            port = 6379;
        }
        JedisPool pool = null;
        Jedis jedis = null;
        try{

            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(128);
            config.setMaxIdle(32);
            config.setMaxWaitMillis(5000);
            pool = new JedisPool(config, host, port, 2000, password);
            jedis = pool.getResource();

            String cmd;
            String[] args;
            if(command.contains(" ")){
                cmd = command.substring(0, command.indexOf(" "));
                args = command.substring(command.indexOf(" ") + 1).split(" ");
            }else{
                cmd = command;
                args = null;
            }

            return execRedisCommand(jedis, cmd, args);
        }catch (JedisConnectionException e){
            return e.getMessage();
        }catch(Exception e){
            LOGGER.error("RedisViewController redisView error ! {}", JSON.toJSONString(e));
            return e.getMessage();
        }finally {
            if(jedis != null){
                jedis.close();
            }
            if(pool != null){
                pool.close();
            }
        }


    }

    private static List<String> execRedisCommand(Jedis jedis, String command, String... args) throws InvocationTargetException, IllegalAccessException {
        Protocol.Command cmd = Protocol.Command.valueOf(command.toUpperCase());
        Client client = jedis.getClient();

        Method method;
        if(args == null){
            method =  MethodUtils.getMatchingMethod(Client.class, "sendCommand", Protocol.Command.class);
            method.setAccessible(true);
            method.invoke(client, cmd);
        }else{
            method =  MethodUtils.getMatchingMethod(Client.class, "sendCommand", Protocol.Command.class, String[].class);
            method.setAccessible(true);
            method.invoke(client, cmd, args);
        }

        try {
            List<String> respList = new ArrayList<>();
            Object response = client.getOne();
            if (response instanceof List) {
                //noinspection rawtypes
                for (Object itemResp : ((List) response)) {
                    respList.add(new String((byte[]) itemResp));
                }
                return respList;
            } else if(response instanceof Long){
                return Collections.singletonList(response.toString());
            } else {
                return Collections.singletonList(new String((byte[]) response));
            }

        } catch (JedisException e) {
            return Collections.singletonList(e.getMessage());
        }
    }

}
