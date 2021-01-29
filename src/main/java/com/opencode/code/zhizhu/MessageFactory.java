package com.opencode.code.zhizhu;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MessageFactory {

    private static List<List<String>> INIT_NAME = new ArrayList<>();

    static{
        try{
            List<String> initName = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("d://names.txt"))));
            String data;
            while((data = br.readLine()) != null){
                if(data.length() == 8){
                    initName.add(data);
                }

            }
            INIT_NAME.addAll(Lists.partition(initName, 2));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

//    0：”大秦铁路”，股票名字；
//            1：”27.55″，今日开盘价；
//            2：”27.25″，昨日收盘价；
//            3：”26.91″，当前价格；
//            4：”27.55″，今日最高价；
//            5：”26.20″，今日最低价；
//            6：”26.91″，竞买价，即“买一”报价；
//            7：”26.92″，竞卖价，即“卖一”报价；
//            8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
//            9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
//            10：”4695″，“买一”申请4695股，即47手；
//            11：”26.91″，“买一”报价；
//            12：”57590″，“买二”
//            13：”26.90″，“买二”
//            14：”14700″，“买三”
//            15：”26.89″，“买三”
//            16：”14300″，“买四”
//            17：”26.88″，“买四”
//            18：”15100″，“买五”
//            19：”26.87″，“买五”
//            20：”3100″，“卖一”申报3100股，即31手；
//            21：”26.92″，“卖一”报价
//            (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
//            30：”2008-01-11″，日期；
//            31：”15:05:32″，时间；

//    private static String clns[] = {
//            "股票名字",
//            "今日开盘价",
//            "昨日收盘价",
//            "当前价格",
//            "今日最高价",
//            "今日最低价",
//            "竞买价，即“买一”报价",
//            "竞卖价，即“卖一”报价",
//            "成交的股票数",
//            "成交金额",
//            "买一申请",
//            "买一报价",
//            "买二",
//            "买二报价",
//            "买三",
//            "买三报价",
//            "买四",
//            "买四报价",
//            "买五",
//            "买五报价",
//            "卖一",
//            "卖一报价",
//            "卖二",
//            "卖二报价",
//            "卖三",
//            "卖三报价",
//            "卖四",
//            "卖四报价",
//            "卖五",
//            "卖五报价",
//            "日期",
//            "时间",
//            "结束符"
//    };

    private static String clns[] = {
            "name",
            "startPrice",
            "endPrice",
            "currentPrice",
            "highPrice",
            "lowPrice",
            "pay1",
            "seller1",
            "dealCount",
            "dealAmount",
            "pay1Application",
            "pay1Offer",
            "pay2",
            "pay2Offer",
            "pay3",
            "p3Offer",
            "pay4",
            "pay4Offer",
            "pay5",
            "pay5Offer",
            "seller1",
            "seller1Offer",
            "seller2",
            "seller2Offer",
            "seller3",
            "seller3Offer",
            "seller4",
            "seller4Offer",
            "seller5",
            "seller5Offer",
            "date",
            "time",
            "EOF"
    };

    public static void main(String[] args){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String pop = MessageCache.pop();
                    if(StringUtils.isEmpty(pop)){
                        continue;
                    }

                    try {
                        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("d://day.txt"),true), true);
                        pw.println(pop);
                        pw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(List<String> names : INIT_NAME){
                    String join = String.join(",", names);
//                    System.out.println(join);
                    String url = "http://hq.sinajs.cn/list=" + join;
                    String message = GetMessageUtils.getMessage(url);
                    String[] split = message.split("\n");

                    List<LinkedHashMap<String,String>> infos = new ArrayList<>();

                    try{
                        for(int i = 0 ; i < split.length ; i++){
                            if(StringUtils.isEmpty(split[i])){
                                continue;
                            }
                            String[] columns = split[i].substring(split[i].indexOf("=") + 2,split[i].length() - 2).split(",");
                            if(columns.length <= 1){
                                continue;
                            }
                            LinkedHashMap<String,String> c = new LinkedHashMap<>();
                            for(int j = 0 ; j < clns.length ; j++){
                                c.put(clns[j],columns[j]);
                            }
//                    infos.add(c);
                            MessageCache.push(JSON.toJSONString(c));
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println(url);
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

}
