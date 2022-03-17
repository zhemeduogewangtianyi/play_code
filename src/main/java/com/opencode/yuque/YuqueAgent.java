package com.opencode.yuque;

import com.opencode.yuque.po.DocCreatePo;
import com.opencode.yuque.po.DocDetailSerializer;
import com.opencode.yuque.po.DocSerializer;
import com.opencode.yuque.po.UserSerializer;
import com.opencode.yuque.vo.DocDetailVo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class YuqueAgent {

    private static final String token = "";

    private static final YuqueApi yuqueApi = new YuqueApi(token);

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        UserSerializer userInfo = yuqueApi.loginUser();

        String weekly = "danube_core/ox30ha";
        String self = "wangtianyi-xhvky/eogv7u";

        String nameSpace = self;

        List<DocSerializer> weeklyList = yuqueApi.listDocByNameSpace(nameSpace);
        DocSerializer docSerializer = weeklyList.get(0);
        if(docSerializer != null){
            DocDetailVo weeklyDetail = yuqueApi.getDocDetailBySlug(nameSpace, docSerializer.getSlug());
            DocDetailSerializer data = weeklyDetail.getData();
            String body = data.getBody();
            System.out.println(body);

            StringBuilder builder = new StringBuilder();
            boolean w = false;
            boolean pre_a = false;
            boolean after_a = false;

            String[] split = body.split("\n");
            for(String line : split){
                if(line.contains("<a name=")){
                    pre_a = true;
                }
                if(pre_a && line.contains("<a name=")){
                    after_a = true;
                }
                if(pre_a && line.contains("AAA")){
                    w = true;
                }
                if(w && pre_a && line.contains("<a name=")){
                    after_a = true;
                }
                if(w && pre_a && after_a){
                    if(line.contains("本周工作")){

                        line +="\n";
                        line += " - 1.asd\n";
                        line += " - 2.asd\n";
                        line += " - 3.asd\n";


                        w = false;
                        pre_a = false;
                        after_a = false;
                    }
                }
                builder.append(line).append("\n");
            }

//            System.out.println(builder.toString());
            data.setBody("123123123");
//            System.out.println(data);

            DocCreatePo updatePo = new DocCreatePo();
            updatePo.setTitle(data.getTitle());
            updatePo.setFormat(data.getFormat());
            updatePo.setSlug(data.getSlug());
            updatePo.setBody(data.getBody());
            updatePo.setPublic_id(data.getPublic_id());
            String bookId = docSerializer.getBook_id();
            String updateRes = yuqueApi.updateDocByNameSpace(bookId, docSerializer.getId(), updatePo);
            System.out.println(updateRes);
        }


    }




}
