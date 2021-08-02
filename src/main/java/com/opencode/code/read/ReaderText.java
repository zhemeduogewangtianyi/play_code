package com.opencode.code.read;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carrot.sec.interfaces.Operations;
import com.carrot.sec.operation.Operation;
import com.carrot.sec.server.CarrotSearchServer;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReaderText {

    private volatile boolean off = true;
    private volatile boolean exit = false;


    public static void main(String[] args) throws IOException, InterruptedException {

        //启动数据库
//        new Thread(new CarrotSearchServer(9527)).start();

        new ReaderText().read();

    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    private void read(){

        new Thread(new Runnable() {

            private volatile String url = "https://www.shuquge.com/txt/127585";

            @SneakyThrows
            @Override
            public void run() {

                while(!exit){

                    //查
                    Operations operations = new Operation("jdbc:carrot-search://127.0.0.1:9527/temp","root", "root");

                    XiYouZhaoJiuWanWu xyzjww = new XiYouZhaoJiuWanWu();
                    List<Map<String, Object>> res = null;

                    try{
                        res = operations.select(xyzjww, 0, 1000000);
                    }catch (Throwable e){

                    }

                    String addr ;

                    XiYouZhaoJiuWanWu xiYouZhaoJiuWanWu = null;

                    String suffix = "33031813.html";
                    if(CollectionUtils.isEmpty(res)){
//                        suffix = "35087186.html";
                        addr = url + "/" + suffix;
                    }else{
                        xiYouZhaoJiuWanWu = JSONObject.parseObject(JSON.toJSONString(res.get(res.size() - 1)), XiYouZhaoJiuWanWu.class);
                        addr = url + "/" + xiYouZhaoJiuWanWu.getNextPage();
                    }

                    List<String> read = new ArrayList<>();
                    String title;
                    String[] contents = xiYouZhaoJiuWanWu == null ? null : JSONArray.parseArray(xiYouZhaoJiuWanWu.getContent()).toArray(new String[]{});
                    int line = 0 ;
                    String nextPage;

                    if(xiYouZhaoJiuWanWu == null || contents.length == xiYouZhaoJiuWanWu.getLineNum()){

                        HttpResponse<String> response = Unirest.get(addr).asString();

                        Document parse = Jsoup.parse(JSONObject.parseObject(JSON.toJSONString(response)).get("body").toString());

                        Element body = parse.body();
                        nextPage = body.select("#wrapper > div.book.reader > div.content > div.page_chapter > ul > li:nth-child(3) > a").attr("href");

                        title = body.select("#wrapper > div.book.reader > div.content > h1").text();

                        Elements select = body.select("#content");
                        contents = select.text().substring(0, select.text().indexOf("http")).split(" ");

                        read.add(title);
                        for(String content : contents){
                            read.add(content);
                        }

                        suffix = xiYouZhaoJiuWanWu != null ? xiYouZhaoJiuWanWu.getNextPage() : suffix;
                        xiYouZhaoJiuWanWu = save(suffix,title,read.toArray(new String[]{}),0,nextPage,true);

                    }else{

                        nextPage = xiYouZhaoJiuWanWu.getNextPage();
                        title = xiYouZhaoJiuWanWu.getTitle();
                        suffix = xiYouZhaoJiuWanWu.getId();
                        line = xiYouZhaoJiuWanWu.getLineNum() - 1;
                        for(String content : contents){
                            read.add(content);
                        }
                    }

                    if (off && !exit) {
                        for (int i = line; i < read.size(); i++) {
                            if(off && !exit){

                                String s = read.get(i);
                                System.out.println(s + " -> " + suffix);
                                Runtime.getRuntime().exec("say " + s);

                                xiYouZhaoJiuWanWu = save(suffix,title,read.toArray(new String[0]), i + 1,nextPage,false);

                                Thread.sleep(s.length() * 250L);
                            }else if(exit) {
                                break;
                            }else{
                                i--;
                            }
                        }
                    }
                    Thread.sleep(50);
                }


            }

        }).start();

        Scanner sc = new Scanner(System.in);
        while (!exit) {
            String next = sc.next();
            if (next.equals("1")) {
                this.off = false;
            } else if (next.equals("2")) {
                this.off = true;
            } else if (next.equals("q")) {
                this.exit = true;
            }

        }
    }

    private XiYouZhaoJiuWanWu save(String suffix,String title,String[] contents,int i,String nextPage,boolean isSave) throws Throwable {
        //存
        Operations save = new Operation("jdbc:carrot-search://127.0.0.1:9527/temp","root", "root");
        XiYouZhaoJiuWanWu save_xyzjww = new XiYouZhaoJiuWanWu();
        save_xyzjww.setId(suffix);
        save_xyzjww.setTitle(title);
        save_xyzjww.setContent(JSONArray.toJSONString(contents));
        save_xyzjww.setLineNum(i);
        save_xyzjww.setNextPage(nextPage);
        if(isSave){
            boolean update = save.create(save_xyzjww);
        }else{
            boolean update = save.update(save_xyzjww);
        }
        return save_xyzjww;
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
