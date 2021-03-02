package com.opencode.code.tomcat.one.server;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private InputStream is;

    private String uri;

    public Request(InputStream is) {
        this.is = is;
    }

    /**
     * 用于解析 HTTP 请求中的原始数据。
     * parseUri() ：解析 HTTP 请求的 URL，将 URI 存储在变量 uri 中， 调用 getUri() 返回 HTTP 请求的 URL
     * */
    public void parse(){

        //GET /index.html HTTP/1.1
        StringBuffer request = new StringBuffer(2048);
        int i ;
        byte[] buffer = new byte[2048];
        try{
            i = is.read(buffer);
        }catch(IOException e){
            e.printStackTrace();
            i = -1;
        }

        for(int j = 0 ; j < i ; j++){
            request.append((char) buffer[j]);
        }

        System.out.println(request.toString());

        this.uri = parseUri(request.toString());

    }

    public String parseUri(String requestString){
        int index1,index2;
        index1 = requestString.indexOf(' ');
        if(index1 != -1){
            index2 = requestString.indexOf(' ',index1 + 1);
            if(index2 > index1){
                return requestString.substring(index1 + 1 , index2);
            }
        }

        return null;
    }

    public String getUri(){
        return this.uri;
    }
}
