package com.opencode.code.tomcat.codeview01;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class StartTomcat {

    public static void main(String[] args) throws LifecycleException, InterruptedException {

        Tomcat tomcat = new Tomcat();

        //创建连接器
        Connector connector = new Connector();
        connector.setPort(8088);
        //Connector 注册到 Service 中
        tomcat.getService().addConnector(connector);

        //注册 Servlet
        Context ctx = tomcat.addContext("/", null);
        Tomcat.addServlet(ctx,"helloServlet","com.opencode.code.tomcat.HelloWorldServlet");

        //映射 Servlet
        ctx.addServletMappingDecoded("/hello","helloServlet");

        tomcat.start();

        tomcat.getServer().await();

//        start();
    }

    public static void start() throws LifecycleException {
        //构建tomcat对象,此对象为启动tomcat服务的入口对象
        Tomcat t = new Tomcat();
        //构建Connector对象,此对象负责与客户端的连接.
        Connector con = new Connector("HTTP/1.1");
        //设置服务端的监听端口
        con.setPort(8080);
        //将Connector注册到service中
        t.getService().addConnector(con);
        //注册servlet
        Context ctx = t.addContext("/", null);
        Tomcat.addServlet(
                ctx,
                "helloServlet",
                "com.company.java.servlet.HelloServlet");
        //映射servlet
        ctx.addServletMappingDecoded("/hello", "helloServlet");
        //启动tomcat
        t.start();
        //阻塞当前线程
        System.out.println(Thread.currentThread().getName());
        t.getServer().await();
    }

}
