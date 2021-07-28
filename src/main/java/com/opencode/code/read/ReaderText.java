package com.opencode.code.read;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ReaderText {

    public static void main(String[] args) throws IOException {

        HttpResponse<String> response = Unirest.get("https://www.shuquge.com/txt/127585/34690990.html").asString();

        Document parse = Jsoup.parse(JSONObject.parseObject(JSON.toJSONString(response)).get("body").toString());

        Element body = parse.body();
        String title = body.select("#wrapper > div.book.reader > div.content > h1").text();
        Elements select = body.select("#content");
        String content = select.text().substring(0, select.text().indexOf("http"));

        Runtime.getRuntime().exec("say " + title + " " + content);

    }

//    private static void read1(){
//        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
//        Dispatch sapo = sap.getObject();
//        try {
//
//            // 音量 0-100
//            sap.setProperty("Volume", new Variant(100));
//            // 语音朗读速度 -10 到 +10
//            sap.setProperty("Rate", new Variant(2));
//
//
//            System.out.println("请输入要朗读的内容：");
//            Scanner scan=new Scanner(System.in);
//            String str=scan.next();
//            // 执行朗读
//            Dispatch.call(sapo, "Speak", new Variant(str));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            sapo.safeRelease();
//            sap.safeRelease();
//        }
//    }

}
