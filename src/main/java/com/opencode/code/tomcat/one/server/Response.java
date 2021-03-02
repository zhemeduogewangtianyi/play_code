package com.opencode.code.tomcat.one.server;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * HTTP Response = Status-Line
 * *(( general-header | response-header | entity-heander ) CRLF)
 * CRLF
 * [ message-body ]
 * Status-Line = HTTP-Version SP Status-Code SP Response-Phrase CRLF
 */
public class Response {

    private static final int BUFFER_SIZE = 1024;

    Request request;

    OutputStream os;

    public Response(OutputStream os) {
        this.os = os;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {

        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try{

            File file = new File(HttpServer.WEB_ROOT,request.getUri());

            if(file.exists()){
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0 , BUFFER_SIZE);
                while(ch != -1){
                    os.write(bytes , 0 , ch);
                    ch = fis.read(bytes , 0 , BUFFER_SIZE);
                }
            }else{
//                new File(HttpServer.WEB_ROOT).mkdirs();

                String errorMessage =
                        "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                os.write(errorMessage.getBytes());
            }

        }catch(Exception e){
            //在不能实例化文件对象的时候抛出异常 - 404
            System.out.println(e.toString());
        }finally {
            if(fis != null){
                fis.close();
            }
        }

    }
}
