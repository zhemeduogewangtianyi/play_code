package com.opencode.code.scheduler.ibf;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProviderRequestParamManager {

    private static final String pictureUrl = "https://fbi.alibaba-inc.com/dashboard/view/page.htm?id=663558";

    public static void main(String[] args) {
        ProviderRequestParamManager pd = new ProviderRequestParamManager();
        List<String> strings = pd.providerPictureData();

        for(String time : strings){
            b:for(String t :strings){
                if(time.equals(t)){
                    continue b;
                }
                System.out.println(time + " -> " + t);
                String url = pictureUrl + "&startTime=" + time + "&endTime=" + t;
                requestData(url);
            }
        }
    }

//    public static void provider(){
//
//        MockRedis.push();
//    }

    private List<String> providerPictureData(){

        List<String> result = new ArrayList<>(30);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for(int i = 0 ; i < 30 ; i++){
            calendar.add(Calendar.DATE, -1);
            calendar.get(Calendar.DATE);
            Date time = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            result.add(sdf.format(time));
        }
        return result;
    }

//    private List<String> providerListData(){
//
//    }


    private static void requestData(String url)  {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();


        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);


        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
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
    }

}
