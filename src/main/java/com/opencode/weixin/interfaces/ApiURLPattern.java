package com.opencode.weixin.interfaces;

import java.util.regex.Pattern;

/**
 * URL 规则解析
 */
public interface ApiURLPattern {

    /**
     * 获取登录 uuid
     * https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1628150495390
     */
    Pattern UUID_PATTERN = Pattern.compile("window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";");

    /**
     * 检查登录 window.code=400
     * */
    Pattern CHECK_LOGIN_PATTERN   = Pattern.compile("window.code=(\\d+)");

    /** 登录的跳转地址 */
    Pattern PROCESS_LOGIN_PATTERN = Pattern.compile("window.redirect_uri=\"(\\S+)\";");


}
