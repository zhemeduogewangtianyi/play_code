package com.opencode.weixin.api;

import com.opencode.weixin.config.WeChatBotConfig;
import com.opencode.weixin.constant.StateCode;
import com.opencode.weixin.entity.Account;
import com.opencode.weixin.entity.LoginSession;
import com.opencode.weixin.entity.Recommend;
import com.opencode.weixin.entity.SyncCheckRet;
import com.opencode.weixin.interfaces.WeChatApi;
import com.opencode.weixin.request.BaseRequest;
import com.opencode.weixin.utils.DateUtils;
import com.opencode.weixin.utils.QRCodeUtils;
import com.opencode.weixin.utils.WeChatUtils;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class WeChatApiImpl extends WeChatAccountBaseInfo implements WeChatApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatApiImpl.class);

    static{
        Unirest.config().enableCookieManagement(true);
    }

    /** uuid */
    private String uuid;

    /** 登录 session */
    private LoginSession loginSession;

    private boolean logging;

    /** 联系人数量 */
    private int memberCount;


    public String getUUID() {

        HttpResponse<String> uuid_response = Unirest.get(WEI_XIN_HOME)
                .headers(GET_HEADER)
                .queryString("appId", "wx782c26e4c19acffb")
                .queryString("fun", "new").asString();

        Matcher matcher = UUID_PATTERN.matcher(uuid_response.getBody());
        if (matcher.find() && StateCode.SUCCESS.equals(matcher.group(1))) {
            this.uuid = matcher.group(2);
        }

        return uuid;

    }


    public void getQrImage(String uuid, boolean terminalShow) {
        String uid = null != uuid ? uuid : this.uuid;
//        String imgDir = bot.config().assetsDir();
        //TODO wty 配置化
        String imgDir = WeChatBotConfig.INSTANCE.assetsDir();

        HttpResponse<byte[]> qrcode_response = Unirest.get(String.format(WEI_XIN_QR_CODE, uid))
                .headers(GET_HEADER).asBytes();

        byte[] body = qrcode_response.getBody();

        InputStream is = new ByteArrayInputStream(body);
        File qrCode = WeChatUtils.saveFile(is, imgDir, "qrcode.png");
        DateUtils.sleep(200);
        try {
            QRCodeUtils.showQrCode(qrCode, terminalShow);
        } catch (Exception e) {
            this.getQrImage(uid, terminalShow);
        }
    }

    @Override
    public String checkLogin(String uuid) {
        String uid = null != uuid ? uuid : this.uuid;
        Long time = System.currentTimeMillis();

        HttpResponse<String> response = Unirest.get(WEI_XIN_CHECK_LOGIN)
                .headers(GET_HEADER)
                .queryString("loginicon", true)
                .queryString("uuid", uid)
                .queryString("tip", "1")
                .queryString("_", time)
                .queryString("r", (int) (-time / 1000) / 1579)
                .connectTimeout(30).asString();


        Matcher matcher = CHECK_LOGIN_PATTERN.matcher(response.getBody());
        if (matcher.find()) {
            if (StateCode.SUCCESS.equals(matcher.group(1))) {
                if (!this.processLoginSession(response.getBody())) {
                    return StateCode.FAIL;
                }
                return StateCode.SUCCESS;
            }
            return matcher.group(1);
        }
        return StateCode.FAIL;
    }

    @Override
    public boolean processLoginSession(String loginContent) {

//        LoginSession loginSession = bot.session();
        LoginSession loginSession = this.loginSession;

        Matcher matcher = PROCESS_LOGIN_PATTERN.matcher(loginContent);
        if (matcher.find()) {
            loginSession.setUrl(matcher.group(1));
        }

        HttpResponse<String> login_redirect_response = Unirest.get(loginSession.getUrl())
                .headers(GET_HEADER).asString();

        System.out.println(login_redirect_response.getBody());

        loginSession.setUrl(loginSession.getUrl().substring(0, loginSession.getUrl().lastIndexOf("/")));

        String body = login_redirect_response.getBody();

        List<String> fileUrl = new ArrayList<>();
        List<String> syncUrl = new ArrayList<>();
        for (int i = 0; i < FILE_URL.size(); i++) {
            fileUrl.add(String.format(WEI_XIN_FILE, FILE_URL.get(i)));
            syncUrl.add(String.format(WEI_XIN_WEB_PUSH, WEB_PUSH_URL.get(i)));
        }
        boolean flag = false;
        for (int i = 0; i < FILE_URL.size(); i++) {
            String indexUrl = INDEX_URL.get(i);
            if (loginSession.getUrl().contains(indexUrl)) {
                loginSession.setFileUrl(fileUrl.get(i));
                loginSession.setSyncUrl(syncUrl.get(i));
                flag = true;
                break;
            }
        }
        if (!flag) {
            loginSession.setFileUrl(loginSession.getUrl());
            loginSession.setSyncUrl(loginSession.getUrl());
        }

        loginSession.setDeviceId("e" + System.currentTimeMillis());

        BaseRequest baseRequest = new BaseRequest();
        loginSession.setBaseRequest(baseRequest);

        loginSession.setSKey(WeChatUtils.match("<skey>(\\S+)</skey>", body));
        loginSession.setWxSid(WeChatUtils.match("<wxsid>(\\S+)</wxsid>", body));
        loginSession.setWxUin(WeChatUtils.match("<wxuin>(\\S+)</wxuin>", body));
        loginSession.setPassTicket(WeChatUtils.match("<pass_ticket>(\\S+)</pass_ticket>", body));

        baseRequest.setSkey(loginSession.getSKey());
        baseRequest.setSid(loginSession.getWxSid());
        baseRequest.setUin(loginSession.getWxUin());
        baseRequest.setDeviceID(loginSession.getDeviceId());

        return true;
    }

    public String pushLogin() {

//        Unirest.config().getClient().getClient()
//        String uin = this.client.cookie("wxUin");
//        if (StringUtils.isEmpty(uin)) {
//            return null;
//        }
//        String url = String.format("%s/cgi-bin/mmwebwx-bin/webwxpushloginurl?uin=%s",
//                Constant.BASE_URL, uin);
//
//        JsonResponse jsonResponse = this.client.send(new JsonRequest(url));
//        return jsonResponse.getString("uuid");

        return null;
    }

    public static void main(String[] args) {
        WeChatApiImpl apiRequest = new WeChatApiImpl();
//        apiRequest.getQrImage(apiRequest.getUUID(), true);
//        String s = apiRequest.checkLogin(apiRequest.getUUID());
//        String s = apiRequest.checkLogin(apiRequest.getUUID());
        apiRequest.login(false);
//        System.out.println(s);
    }

    @Override
    public void login(boolean autoLogin) {
//        if (bot.isRunning() || logging) {
//            LOGGER.warn("微信已经登录");
//            return;
//        }
        if (autoLogin) {
//            this.autoLogin();
        } else {
            this.logging = true;
            while (logging) {
                this.uuid = pushLogin();
                if (null == this.uuid) {
                    while (null == this.getUUID()) {
                        DateUtils.sleep(10);
                    }
                    LOGGER.info("开始下载二维码");
//                    this.getQrImage(this.uuid, bot.config().showTerminal());
                    this.getQrImage(this.uuid, false);
                    LOGGER.info("请使用手机扫描屏幕二维码");
                }
                Boolean isLoggedIn = false;
                while (null == isLoggedIn || !isLoggedIn) {
                    String status = this.checkLogin(this.uuid);
                    if (StateCode.SUCCESS.equals(status)) {
                        isLoggedIn = true;
                    } else if ("201".equals(status)) {
                        if (null != isLoggedIn) {
                            LOGGER.info("请在手机上确认登录");
                            isLoggedIn = null;
                        }
                    } else if ("408".equals(status)) {
                        break;
                    }
                    DateUtils.sleep(300);
                }
                if (null != isLoggedIn && isLoggedIn) {
                    break;
                }
                if (logging) {
                    LOGGER.info("登录超时，重新加载二维码");
                }
            }
        }

//        this.webInit();
//        this.statusNotify();
        this.loadContact(0);

        LOGGER.info("应有 {} 个联系人，读取到联系人 {} 个", this.memberCount, this.accountMap.size());
        System.out.println();

        LOGGER.info("共有 {} 个群 | {} 个直接联系人 | {} 个特殊账号 ｜ {} 公众号或服务号",
                this.groupUserNames.size(), this.contactList.size(),
                this.specialUsersList.size(), this.publicUsersList.size());

        // 加载群聊信息，群成员
//        this.loadGroupList();

//        LOGGER.info("[{}] 登录成功.", bot.session().getNickName());
        LOGGER.info("[{}] 登录成功.", loginSession.getNickName());
//        this.startRevive();
        this.logging = false;
    }

    @Override
    public void logout() {

    }

    @Override
    public void loadContact(int seq) {

    }

    @Override
    public SyncCheckRet syncCheck() {
        return null;
    }

    @Override
    public boolean sendText(String toUser, String msg) {
        return false;
    }

    @Override
    public boolean sendImg(String toUser, String filePath) {
        return false;
    }

    @Override
    public boolean sendFile(String toUser, String filePath) {
        return false;
    }

    @Override
    public Account getAccountById(String id) {
        return null;
    }

    @Override
    public Account getAccountByName(String name) {
        return null;
    }

    @Override
    public boolean revokeMsg(String msgId, String toUser) {
        return false;
    }

    @Override
    public boolean verify(Recommend recommend) {
        return false;
    }

    @Override
    public boolean addFriend(String friend, String msg) {
        return false;
    }

    @Override
    public boolean createChatRoom(String topic, List<String> members) {
        return false;
    }

    @Override
    public boolean removeMemberByGroup(String member, String group) {
        return false;
    }

    @Override
    public boolean inviteJoinGroup(String member, String group) {
        return false;
    }

    @Override
    public boolean modifyGroupName(String oldTopic, String newTopic) {
        return false;
    }
}
