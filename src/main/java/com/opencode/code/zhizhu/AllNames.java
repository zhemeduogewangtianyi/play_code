package com.opencode.code.zhizhu;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class AllNames {

    public static void main(String[] args) {

        for(int x = 1 ; x < 98 ; x++){
            execute(x+"");
        }

    }

    public static void execute(String current){
        String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p="+current+"&sr_p=-1";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {

                String html = EntityUtils.toString(responseEntity);
                Document document = Jsoup.parse(html);
                Elements select = document.select("body > script:nth-child(2)");
                String sourceNameHtml = select.html();
                Document sourceNameDoc = Jsoup.parse(sourceNameHtml);
                Elements sourceNames = sourceNameDoc.select("body");
                String sourceName = sourceNames.text();

                String[] split = sourceName.split(";");

                String sourceNameStr = sourceName.substring(split[0].indexOf("=") + 3, split[0].length() - 1);
                String[] name = sourceNameStr.split(",");
                for(String n : name){
                    File file = new File("d://names.txt");
                    PrintWriter pw = new PrintWriter(new FileOutputStream(file,true),true);
                    pw.println(n);
                    pw.close();
                }

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
