package com.opencode.dingding.interfaces.impl;

import com.alibaba.fastjson.JSON;
import com.opencode.dingding.utils.DingResponseRouteUtil;
import com.opencode.dingding.addr.Constant;
import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.websocket.reg.RegResponse;
import com.opencode.dingding.enums.DingConfigTopicEnum;
import com.opencode.dingding.enums.UnlimitedTypeEnum;
import com.opencode.dingding.interfaces.LongInterfaces;
import com.opencode.dingding.utils.DingDingUtils;
import org.java_websocket.client.WebSocketClient;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LongInterfaceImpl implements LongInterfaces {

    private final CheckLoginMessage message;

    private final WebSocketClient webSocketClient;

    public LongInterfaceImpl(WebSocketClient webSocketClient,CheckLoginMessage message) {
        this.message = message;
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void reg() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/reg");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingResponseRouteUtil.mackMid(RegResponse.class));
            put("cache-header", "token app-key did ua vhost wv");
            put("vhost", "WK");
            put("wv", "im:3,au:3,sy:4");
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void subscribe() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/subscribe");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("set-ver", "224175173483");
            put("sync", "0;0;0;0;");
            put("cache-header", "token app-key did ua vhost wv");
            put("vhost", "WK");
            put("wv", "im:3,au:3,sy:4");
            put("token", message.getAccessToken());
            put("appKey", message.getAppKey());
            put("sid", message.getSid());
            put("did", message.getDid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getSwitchStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Sync/getSwitchStatus");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void log() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/LogI/log");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new ArrayList<Map<String,Object>>(){{
            add(new HashMap<String,Object>(){{
                put("code",2);
                put("uid",message.getUserProfileExtensionModel().getCardUserModel().getUid());
                put("app", Constant.DD_WEB);
                put("appVer", message.getUserProfileExtensionModel().getUserProfileModel().getVer());
                put("os", "MAC OS ++");
                put("osVer", "Unknown");
                put("manufacturer", "");
                put("model", "");
                put("level", 1);
                put("message", "gmkey:stay,gokey:stayName=auth&stayTime=" + Math.random() * 10000 + 100);
            }});
        }});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getConfirmStatusInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/IDLDing/getConfirmStatusInfo");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getPreferBizCallNum() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/ConferenceI/getPreferBizCallNum");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void createTempSessionInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/LoginI/createTempSessionInfo");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getByIdUnlimited(UnlimitedTypeEnum unlimitedTypeEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/IDLConversation/getByIdUnlimited");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ unlimitedTypeEnum.getValue() + message.getOpenId() });
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getConf(DingConfigTopicEnum topicEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/CsConfigI/getConf");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new ArrayList<Map<String,Object>>(){{
            add(new HashMap<String,Object>(){{
                put("topic",topicEnum.getValue());
                put("version", 0);
            }});
        }});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getUserProfileByUids() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/UserMixI/getUserProfileByUids");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ message.getOpenId() });
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void queryUserDingMailStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/CMailI/queryUserDingMailStatus");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ "zh_CN" });
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void listNewest() {

        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/IDLConversation/listNewest");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Integer[]{1000});

        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getState() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Sync/getState");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new ArrayList<Map<String,Object>>(){{
            add(new HashMap<String,Object>(){{
                put("pts",0);
            }});
            add(new HashMap<String,Object>(){{
                put("highPts",0);
            }});
            add(new HashMap<String,Object>(){{
                put("seq",0);
            }});
            add(new HashMap<String,Object>(){{
                put("timestamp",0);
            }});
            add(new HashMap<String,Object>(){{
                put("tooLong2Tag","");
            }});
        }});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void IDLSend(String conversationId,String text) {

        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/IDLSend/send");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new ArrayList<Map<String,Object>>(){{
            add(new HashMap<String,Object>(){{
                put("uuid", UUID.randomUUID().toString());
            }});
            add(new HashMap<String,Object>(){{
                put("conversationId",conversationId);
            }});
            add(new HashMap<String,Object>(){{
                put("type",1);
            }});
            add(new HashMap<String,Object>(){{
                put("creatorType",1);
            }});
            add(new HashMap<String,Object>(){{
                put("content",new HashMap<String,Object>(){{
                    put("contentType",1);
                    put("textContent",new HashMap<String,String>(){{
                        put("text",text);
                    }});
                    put("atOpenIds",new HashMap<>());
                }});
            }});
            add(new HashMap<String,Object>(){{
                put("nickName" , message.getNick());
            }});
        }});
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getUserProfileExtensionByUid() {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/UserMixI/getUserProfileExtensionByUid");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ message.getOpenId() , null });
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void listMembers(String conversationId) {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/IDLConversation/listMembers");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ conversationId , 0 , Integer.MAX_VALUE });
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void listMessages(String conversationId) {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/IDLMessage/listMessages");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ conversationId , false , System.currentTimeMillis() , 14 });
        webSocketClient.send(JSON.toJSONString(map));
    }

    @Override
    public void getUserEmployeeInfos(String[] userId,String orgId) {
        Map<String, Object> map = new HashMap<>();
        map.put("lwp", "/r/Adaptor/ContactI/getUserEmployeeInfos");
        map.put("headers", new HashMap<String, Object>() {{
            put("mid", DingDingUtils.mackMid());
            put("ua", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 OS(macosx/10.14.6) Browser(chrome/92.0.4515.159) DingWeb/3.8.10 LANG/zh_CN");
        }});
        map.put("body",new Object[]{ userId , orgId });
        webSocketClient.send(JSON.toJSONString(map));
    }

    public static void main(String[] args) {
        LongInterfaces longInterfaces = new LongInterfaceImpl(null,null);
        Class<? extends LongInterfaces> aClass = longInterfaces.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for(Method method : declaredMethods){
            System.out.println(method.getName() + " -> " + method.getParameters().length);
        }
    }

}
