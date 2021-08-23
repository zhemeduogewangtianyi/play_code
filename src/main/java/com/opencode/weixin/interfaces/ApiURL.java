package com.opencode.weixin.interfaces;

import java.util.*;

public interface ApiURL extends ApiURLPattern {

    /** 微信登录首页 */
    String WEI_XIN_LOGIN_INDEX = "https://login.weixin.qq.com/jslogin";

    /** 二维码 */
    String WEI_XIN_QR_CODE = "https://login.weixin.qq.com/qrcode/%s";

    /** 检查登录 */
    String WEI_XIN_CHECK_LOGIN = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login";

    /** 文件 */
    String WEI_XIN_FILE = "https://%s/cgi-bin/mmwebwx-bin";

    /** 网络推送 */
    String WEI_XIN_WEB_PUSH = "https://%s/cgi-bin/mmwebwx-bin";

    /** 版本 */
    String VERSION           = "1.0.5";
    /** 微信首页 */
    String WEI_XIN_HOME = "https://login.weixin.qq.com";
    String GET               = "GET";
    String GROUP_BR          = ":<br/>";
    String GROUP_IDENTIFY    = "@@";
    String LOCATION_IDENTIFY = "/cgi-bin/mmwebwx-bin/webwxgetpubliclinkimg?url=";

    String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36";

    /**
     * 特殊用户 须过滤
     */
    Set<String> API_SPECIAL_USER = new HashSet<>(
            Arrays.asList("newsapp", "filehelper", "weibo", "qqmail",
                    "fmessage", "tmessage", "qmessage", "qqsync",
                    "floatbottle", "lbsapp", "shakeapp", "medianote",
                    "qqfriend", "readerapp", "blogapp", "facebookapp",
                    "masssendapp", "meishiapp", "feedsapp", "voip",
                    "blogappweixin", "brandsessionholder", "weixin",
                    "weixinreminder", "officialaccounts", "wxitil",
                    "notification_messages", "wxid_novlwrv3lqwv11",
                    "gh_22b87fa7cb3c", "userexperience_alarm"));

    /**
     * index url
     */
    List<String> INDEX_URL = new ArrayList<>(
            Arrays.asList("wx2.qq.com", "wx8.qq.com",
                    "wx.qq.com", "web2.wechat.com", "wechat.com"));

    /**
     * file url
     */
    List<String> FILE_URL = new ArrayList<>(
            Arrays.asList("file.wx2.qq.com", "file.wx8.qq.com",
                    "file.wx.qq.com", "file.web2.wechat.com", "file.web.wechat.com"));

    /**
     * webpush url
     */
    List<String> WEB_PUSH_URL = new ArrayList<>(
            Arrays.asList("wx2.qq.com", "wx8.qq.com",
                    "wx.qq.com", "web2.wechat.com", "web.wechat.com"));

}
