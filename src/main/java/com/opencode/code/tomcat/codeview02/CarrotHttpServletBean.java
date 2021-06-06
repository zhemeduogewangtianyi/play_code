package com.opencode.code.tomcat.codeview02;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.regex.Matcher;

public class CarrotHttpServletBean extends HttpServlet {

    public static void main(String[] args) {
        ProtectionDomain protectionDomain = CarrotHttpServletBean.class.getProtectionDomain();
        ClassLoader classLoader = protectionDomain.getClassLoader();
        String file = protectionDomain.getCodeSource().getLocation().getFile();
        Package aPackage = CarrotHttpServletBean.class.getPackage();
        String s = aPackage.getName().replaceAll("\\.", Matcher.quoteReplacement(File.separator));
        String s1 = file + s;
        String substring = s1.replaceAll("/", Matcher.quoteReplacement(File.separator)).substring(1);
        File f = new File(s1);
        File[] files = f.listFiles();
        for(File clsFile : files){
            if(clsFile.getName().endsWith(".class")){
                
            }
        }
        System.out.println(substring);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
