package com.opencode.car.base.brand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.car.entity.spider.brand.BrandItems;
import com.opencode.car.entity.spider.brand.BrandList;
import com.opencode.car.entity.spider.brand.BrandResult;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrandService {

    private static final String brandJsonPath = System.getProperty("user.dir") + File.separator + "src/main/java/com/opencode/car/base/brand/brand.json";

    private static final List<BrandItems> brandList = new ArrayList<>();

    static {
        initData();
    }

    public static void initData(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(brandJsonPath)), "utf-8"));
            String data;
            while((data = br.readLine()) != null){
                BrandResult brandResult = JSONObject.parseObject(data, BrandResult.class);
                if(brandResult != null){
                    BrandList result = brandResult.getResult();
                    if(result != null){
                        List<BrandItems> brandlist = result.getBrandlist();
                        if(!CollectionUtils.isEmpty(brandlist)){
                            brandList.addAll(brandlist);
                        }
                    }
                }else{
                    updateBrandList();
                    initData();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateBrandList(){

        String url = "https://api.che168.com/auto/GetCarSeriesByBrandId.ashx?callback=che168brand&type=1&provinceid=330100&_appid=cms";
        HttpResponse<String> response = Unirest.get(url).headers(new HashMap<String, String>() {{
            put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3192.0 Safari/537.36");
            put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            put("Accept-Encoding", "gzip,deflate,sdch");
            put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            put("Connection", "keep-alive");
            put("referer", "https://www.autohome.com.cn/");
        }}).asString();

        if(response != null){
            String body = response.getBody();
            if(StringUtils.isNotBlank(body)){
                String data = body.substring(body.indexOf("{"), body.lastIndexOf(")"));
                BrandResult brandResult = JSONObject.parseObject(data, BrandResult.class);
                File file = new File(brandJsonPath);
                PrintWriter pw = null;
                try {

                    pw = new PrintWriter(new FileOutputStream(file));
                    pw.println(JSON.toJSONString(brandResult));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if (pw != null){
                        pw.close();
                    }
                }

            }
        }

    }

    public static void main(String[] args) {
//        BrandService.updateBrandList();
        System.out.println();
    }

}
