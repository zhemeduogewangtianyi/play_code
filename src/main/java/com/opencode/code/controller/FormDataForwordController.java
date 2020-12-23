package com.opencode.code.controller;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
public class FormDataForwordController {

    @RequestMapping(value = "/exec")
    public Object extecte(HttpServletRequest request){

        Map<String, String[]> parameterMap = ((StandardMultipartHttpServletRequest) request).getParameterMap();


        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();


        // 创建Get请求
        HttpPost httpPost = new HttpPost("http://localhost:8080/test");

//        httpPost.setHeader("Content-Type", "multipart/form-data; boundary=<calculated when request is sent>");

        StringBody token = new StringBody("123456", ContentType.TEXT_PLAIN);
        StringBody input = new StringBody("aaaaaaaaaa", ContentType.TEXT_PLAIN);
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart("token",token);
        multipartEntity.addPart("input",input);

        FileBody fileBody = new FileBody(new File("D:\\test.jpg"),"image/pjpeg","utf-8");

        multipartEntity.addPart("fileBody",fileBody);

        httpPost.setEntity(multipartEntity);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    @RequestMapping(value = "/test")
    public Object test(HttpServletRequest request){
        System.out.println(request);
        return null;
    }

}
