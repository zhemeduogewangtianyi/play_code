package com.opencode.yuque;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opencode.yuque.po.DocCreatePo;
import com.opencode.yuque.po.DocSerializer;
import com.opencode.yuque.po.UserSerializer;
import com.opencode.yuque.vo.DocDetailVo;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YuqueApi {

    private static final String YU_QUE = "https://www.yuque.com/api/v2";

    private String token;

    public YuqueApi(String token) {
        this.token = token;
    }

    /** 登录信息 */
    public UserSerializer loginUser(){
        String url = YU_QUE + "/user";
        HttpResponse<String> response = Unirest.get(url)
                .headers(new HashMap<String, String>() {{
                    put("Content-Type", "application/json");
                    put("User-Agent", "DDDXHH");
                    put("X-Auth-Token", token);
                }}).asString();
        Object res = JSONObject.parseObject(response.getBody()).get("data");
        return JSONObject.parseObject(JSON.toJSONString(res), UserSerializer.class);

    }

    /** 找到 nameSpace 下的文档 */
    public List<DocSerializer> listDocByNameSpace(String nameSpace){
        String url = YU_QUE + "/repos/" + nameSpace + "/docs";
        HttpResponse<String> response = Unirest.get(url)
                .headers(new HashMap<String, String>() {{
                    put("Content-Type", "application/json");
                    put("User-Agent", "DDDXHH");
                    put("X-Auth-Token", token);
                }}).asString();

        JSONObject jsonObject = JSONObject.parseObject(response.getBody());
        return JSONArray.parseArray(JSON.toJSONString(jsonObject.get("data")), DocSerializer.class);

    }


    /** 找到 nameSpace 下的具体某个文档 */
    public DocDetailVo getDocDetailBySlug(String nameSpace , String slug){

        String url = YU_QUE + "/repos/" + nameSpace + "/docs/" + slug;
        HttpResponse<String> response = Unirest.get(url)
                .headers(new HashMap<String, String>() {{
                    put("Content-Type", "application/json");
                    put("User-Agent", "DDDXHH");
                    put("X-Auth-Token", token);
                }}).asString();

        return JSONObject.parseObject(response.getBody() , DocDetailVo.class);

    }

    /** 更新文档 by nameSpace */
    public String updateDocByNameSpace(String nameSpace, String docId, DocCreatePo updatePo)  {

        String url = YU_QUE + "/repos/" + nameSpace + "/docs/" + docId;

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(updatePo));


        jsonObject.put("public",updatePo.getPublic_id().toString());
//        jsonObject.put("_force_asl",1);
        jsonObject.remove("public_id");

        HttpResponse<String> response = Unirest.put(url)
                .headers(new HashMap<String, String>() {{
                    put("Content-Type", "application/json");
                    put("User-Agent", "DDDXHH");
                    put("X-Auth-Token", token);
                }})
                .queryString(jsonObject)
                .asString();

        return response.getBody();
    }

}
