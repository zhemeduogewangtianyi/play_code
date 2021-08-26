package com.opencode.dingding.td;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opencode.dingding.addr.Addrs;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.http.CheckWebLoginResult;
import com.opencode.dingding.entity.response.http.SetCookieResult;
import com.opencode.dingding.enums.DingConfigTopicEnum;
import com.opencode.dingding.enums.UnlimitedTypeEnum;
import com.opencode.dingding.interfaces.LoginInterface;
import com.opencode.dingding.interfaces.LongInterfaces;
import com.opencode.dingding.interfaces.impl.LongInterfaceImpl;
import com.opencode.dingding.manager.ResponseManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class CheckLoginAgent implements Runnable {

    private final LoginInterface loginInterface;

    private final String qrCodeAuth;

    public CheckLoginMessage checkLoginMessage;

    public CheckLoginAgent(LoginInterface loginInterface, String qrCodeAuth) {
        this.loginInterface = loginInterface;
        this.qrCodeAuth = qrCodeAuth;
    }

    public void checkLogin(Thread t) {

        while ((this.checkLoginMessage = loginInterface.checkLogin(this.qrCodeAuth, loginInterface.getParamToken())) == null) {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String tmpCode = this.checkLoginMessage.getTmpCode();
        Long openId = this.checkLoginMessage.getOpenId();

        CheckWebLoginResult checkWebLoginResult = loginInterface.checkWebLogin(openId.toString());
        if (checkWebLoginResult.getSuccess()) {
            SetCookieResult setCookieResult = loginInterface.setCookie(tmpCode);
            if (setCookieResult.getCode() == 200) {
                checkLoginMessage.setDid(setCookieResult.getDid());
                System.out.println(checkLoginMessage);
                t.start();
            }
        }
    }

    @Override
    public void run() {

        try {

            HashMap<String, String> connect_header = new HashMap<String, String>() {{
                put("Upgrade", "websocket");
                put("Connection", "Upgrade");
            }};

            URI uri = new URI(Addrs.LONG);
            WebSocketClient webSocketClient = new WebSocketClient(uri, connect_header) {

                final LongInterfaces longInterfaces = new LongInterfaceImpl(this, checkLoginMessage);

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("onOpen -- ");
                    for (Iterator<String> it = serverHandshake.iterateHttpFields(); it.hasNext(); ) {
                        String key = it.next();
                        System.out.println(key + ":" + serverHandshake.getFieldValue(key));
                    }
                    System.out.println(serverHandshake.getHttpStatus());
                    System.out.println(serverHandshake.getHttpStatusMessage());


                    longInterfaces.reg();

                }

                @Override
                public void onMessage(String s) {

                    ResponseContext context = new ResponseContext();
                    context.setRes(s);
                    context.setMessage(checkLoginMessage);
                    ResponseManager.managerMsg(context);

                    JSONObject jsonObject = JSONObject.parseObject(s);
                    Object o = jsonObject.get("headers");
                    if (o != null) {
                        try {
                            JSONObject sidMap = JSONObject.parseObject(JSON.toJSONString(o));
                            Object mid = sidMap.get("mid");
//                            if (mid != null && mid.toString().equals(openMid)) {
//                                invokeMethod();
//                            }
                            Object sid = sidMap.get("sid");
                            if (sid != null) {
                                checkLoginMessage.setSid(sid.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("onMessage -- ");
                    System.out.println(s);

                }


                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("onClose -- ");
                    System.out.println(i);
                    System.out.println(s);
                    System.out.println(b);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("onError -- ");
                    e.printStackTrace();
                }

                private void invokeMethod() {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true){

                                longInterfaces.subscribe();
                                longInterfaces.getSwitchStatus();
                                longInterfaces.log();
                                longInterfaces.getConfirmStatusInfo();
                                longInterfaces.getPreferBizCallNum();
                                longInterfaces.createTempSessionInfo();

                                for(UnlimitedTypeEnum unlimitedTypeEnum : UnlimitedTypeEnum.values()){
                                    longInterfaces.getByIdUnlimited(unlimitedTypeEnum);
                                }

                                for(DingConfigTopicEnum topicEnum : DingConfigTopicEnum.values()){
                                    longInterfaces.getConf(topicEnum);
                                }

                                longInterfaces.getUserProfileByUids();

                                longInterfaces.queryUserDingMailStatus();

                                longInterfaces.listNewest();

                                longInterfaces.getState();

                                longInterfaces.getUserProfileExtensionByUid();

                                try {
                                    Thread.sleep(2000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }).start();

                }

            };

            webSocketClient.connect();


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
