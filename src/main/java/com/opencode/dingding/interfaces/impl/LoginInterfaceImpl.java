package com.opencode.dingding.interfaces.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.opencode.dingding.addr.Addrs;
import com.opencode.dingding.addr.Constant;
import com.opencode.dingding.addr.Locals;
import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.http.*;
import com.opencode.dingding.interfaces.LoginInterface;
import com.opencode.dingding.utils.DingDingUtils;
import com.opencode.dingding.utils.QRCodeUtils;
import kong.unirest.Headers;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoginInterfaceImpl implements LoginInterface {

    static{
        Unirest.config().enableCookieManagement(true);
    }

    @Override
    public String getQrCodeAuth() {
        HttpResponse<String> response = Unirest.get(Addrs.QR_CODE_AUTH).asString();
        String body = response.getBody();
        String res = body.substring(body.indexOf("{"), body.lastIndexOf("}") + 1);
        QrCodeAuthResult qrCodeAuthResult = JSONObject.parseObject(res, QrCodeAuthResult.class);
        return qrCodeAuthResult == null || !qrCodeAuthResult.isSuccess() ? "" : qrCodeAuthResult.getResult();
    }

    @Override
    public void openQrCode(String qrCodeAuth) {

        String qrUrl = Addrs.QR_CODE + qrCodeAuth;
        System.out.println(qrUrl);
        BufferedImage bufferedImage = QRCodeUtils.generatorRqCode(qrUrl);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            assert bufferedImage != null;
            ImageIO.write(bufferedImage,"png",baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        String imgDir = Locals.CONF_ASSETS_DIR_DEFAULT;
        File qrCode = DingDingUtils.saveFile(bais, imgDir, "qrcode.png");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            QRCodeUtils.showQrCode(qrCode, true);
        } catch (WriterException e) {
            try {
                QRCodeUtils.showQrCode(qrCode, false);
            } catch (WriterException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getParamToken() {
        Map<String,Object> queryParam = new HashMap<String,Object>(){
            {
                put("data","106!" + Arrays.toString(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8))));
                put("xa","dingding");
                put("xt","");
            }
        };
        HttpResponse<String> response = Unirest.get(Addrs.PARAM_TOKEN).queryString(queryParam).asString();
        String body = response.getBody();
        ParamTokenResult paramTokenResult = JSONObject.parseObject(body, ParamTokenResult.class);
        return paramTokenResult == null ? null : paramTokenResult.getTn();
    }

    @Override
    public CheckLoginMessage checkLogin(String qrCodeAuth,String token) {

        String data = "/user/qrcode/is_logged.jsonp?appKey=%s&callback=angular.callbacks._7&pdmModel=Mac+OS+X+10.14.6&pdmTitle=Mac+OS+X+10.14.6+Web&pdmToken=%s&qrcode=%s";
        String path = String.format(data, Constant.APP_KEY, token, qrCodeAuth);

        Map<String,String> headers = new HashMap<String,String>(){
            {
                put("authority","oa.dingtalk.com");
                put("method","GET");
                put("path",path);
                put("scheme","https");
                put("accept","*/*");
                put("accept-encoding","gzip, deflate, br");
                put("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
                put("cache-control","cache-control: no-cache");
                put("pragma","no-cache");
                put("referer","https://im.dingtalk.com/");
                put("sec-ch-ua","\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"");
                put("sec-ch-ua-mobile","?0");
                put("sec-fetch-dest","script");
                put("sec-fetch-mode","no-cors");
                put("sec-fetch-site","same-site");
                put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
            }
        };

        Map<String,Object> queryParam = new HashMap<String,Object>(){
            {
                put("appKey", Constant.APP_KEY);
                put("callback","angular.callbacks._7");
                put("pdmModel","Mac OS X 10.14.6");
                put("pdmTitle","Mac OS X 10.14.6 Web");
                put("pdmToken",token);
                put("qrcode",qrCodeAuth);
            }
        };


        HttpResponse<String> response = Unirest.get(Addrs.CHECK_LOGIN).headers(headers).queryString(queryParam).asString();
        String body = response.getBody();
        CheckLoginMessageResult checkLoginMessageResult = JSONObject.parseObject(body.substring(body.indexOf("{"), body.lastIndexOf("}") + 1), CheckLoginMessageResult.class);
        return checkLoginMessageResult.isSuccess() ? JSONObject.parseObject(checkLoginMessageResult.getResult(),CheckLoginMessage.class) : null ;
    }

    @Override
    public CheckWebLoginResult checkWebLogin(String openId) {
        Map<String,String> headers = new HashMap<String,String>(){
            {
                put("authority","oa.dingtalk.com");
                put("method","GET");
                put("path","/omp/realm/check_web_login.jsonp?callback=angular.callbacks._7&openId="+openId);
                put("scheme","https");
                put("accept","*/*");
                put("accept-encoding","gzip, deflate, br");
                put("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
                put("cache-control","cache-control: no-cache");
                put("pragma","no-cache");
                put("referer","https://im.dingtalk.com/");
                put("sec-ch-ua","\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"");
                put("sec-ch-ua-mobile","?0");
                put("sec-fetch-dest","script");
                put("sec-fetch-mode","no-cors");
                put("sec-fetch-site","same-site");
                put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
            }
        };
        Map<String,Object> queryParam = new HashMap<String,Object>(){
            {
                put("callback","angular.callbacks._7");
                put("openId",openId);
            }
        };
        HttpResponse<String> response = Unirest.get(Addrs.CHECK_WEB_LOGIN).headers(headers).queryString(queryParam).asString();
        String body = response.getBody();
        return JSONObject.parseObject(body.substring(body.indexOf("{"), body.lastIndexOf("}") + 1), CheckWebLoginResult.class);
    }

    @Override
    public SetCookieResult setCookie(String tmpCode) {
        Map<String,Object> queryParam = new HashMap<String,Object>(){
            {
                put("appkey",Constant.APP_KEY);
                put("code",tmpCode);
                put("isSession",true);
                put("callback","__jp0");
            }
        };
        HttpResponse<String> response = Unirest.get(Addrs.SET_COOKIE).queryString(queryParam).asString();
        String body = response.getBody();
        Headers headers = response.getHeaders();
        List<String> list = headers.get("set-cookie");
        String did = list.get(0).substring(9, list.get(0).indexOf(";"));
        SetCookieResult setCookieResult = JSONObject.parseObject(body.substring(body.indexOf("{"), body.lastIndexOf("}") + 1), SetCookieResult.class);
        setCookieResult.setDid(did);
        return setCookieResult;
    }

}
