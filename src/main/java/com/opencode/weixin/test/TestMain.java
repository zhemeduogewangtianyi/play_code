package com.opencode.weixin.test;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.HashMap;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) {

        Map<String,String> headers = new HashMap<String,String>(){
            {
//                put("user-agent","Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) > AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 > Chrome/37.0.0.0 Mobile Safari/537.36 > MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI");
                put("user-agent","Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIFI");
            }
        };

        HttpResponse<String> response = Unirest.get("https://www.homemadecake.cn/sp/order/history")
                .headers(headers).asString();
        String body = response.getBody();
        System.out.println(body);

    }

}
