package com.opencode.code.tomcat;

import javax.servlet.*;
import java.io.IOException;

public class HelloWorldServlet implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.getServletConfig();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("service");
        res.getOutputStream().write("<h1>hahahahhaha</h1>".getBytes());
    }

    @Override
    public String getServletInfo() {
        return this.getServletInfo();
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
