package com.opencode.dingding.manager;

import com.opencode.dingding.abstracts.AbstractResponseRoute;
import com.opencode.dingding.entity.context.ResponseContext;
import com.opencode.dingding.entity.ding.CheckLoginMessage;
import com.opencode.dingding.entity.response.websocket.create_temp_session_info.CreateTempSessionInfoResponse;
import com.opencode.dingding.entity.response.websocket.get_by_id_un_limited.GetByIdUnlimitedResponse;
import com.opencode.dingding.entity.response.websocket.get_confirm_status_info.GetConfirmStatusInfoResponse;
import com.opencode.dingding.entity.response.websocket.get_prefer_biz_call_num.GetPreferBizCallNumResponse;
import com.opencode.dingding.entity.response.websocket.get_switch_status.GetSwitchStatusResponse;
import com.opencode.dingding.entity.response.websocket.list_messages.ListMessagesResponse;
import com.opencode.dingding.entity.response.websocket.log.LogResponse;
import com.opencode.dingding.interfaces.impl.websocket_route.*;
import com.opencode.dingding.utils.DingResponseRouteUtil;
import com.opencode.dingding.entity.response.websocket.WebSocketResponse;
import com.opencode.dingding.entity.response.websocket.reg.RegResponse;
import com.opencode.dingding.entity.response.websocket.subscribe.SubscribeResponse;
import com.opencode.dingding.utils.UnicodeUtil;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResponseManager {

    private static final List<AbstractResponseRoute> handles = new ArrayList<AbstractResponseRoute>() {{

//        Reflections reflections = new Reflections("com.opencode.dingding.interfaces.impl.websocket_route");
//        Set<Class<? extends AbstractResponseRoute>> subTypes = reflections.getSubTypesOf(AbstractResponseRoute.class);
//        subTypes.forEach(sub -> {
//            try {
//                add(sub.newInstance());
//            } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });

        add(new RegResponseRoute());
//        add(new SubscribeResponseRoute());
//        add(new GetSwitchStatusResponseRoute());
//        add(new LogResponseRoute());
//        add(new GetConfirmStatusInfoResponseRoute());
//        add(new GetPreferBizCallNumResponseRoute());
//        add(new CreateTempSessionInfoResponseRoute());
//        add(new GetByIdUnlimitedResponseRoute());
//        add(new ListMessagesResponseRoute());

    }};

    static final List<String> datas = new ArrayList<String>() {{
        String reg_data = "{\"headers\":{\"mid\":\"64b1c9da 0\",\"sid\":\"0b86f5a76124f3bc136da425e4799154366db1c39f15\"},\"code\":200}";
        add(reg_data);

        String subscribe_data = "{\"headers\":{\"dt\":\"j\",\"auth-route-logic-unit\":\"VIP001\",\"reg-sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\",\"reg-uid\":\"570085753@dingding\",\"auth-route-unit\":\"HZ\",\"mid\":\"a5ba001b 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":{\"unitName\":\"HZ\",\"cookie\":\"auth-route-logic-unit=VIP001;auth-route-unit=HZ\",\"timestamp\":1629801278605,\"isFromChina\":true}}";
        add(subscribe_data);

        String get_switch_status_data = "{\"headers\":{\"dt\":\"j\",\"mid\":\"3a72001d 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":1}";
        add(get_switch_status_data);

        String log_data = "{\"headers\":{\"dt\":\"j\",\"mid\":\"00860022 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200}";
        add(log_data);

        String get_confirm_status_info_data = "{\"headers\":{\"dt\":\"j\",\"mid\":\"28fa001e 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":\"{\\\"11\\\":\\\"\\u62D2\\u63A5\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"22\\\":\\\"\\u51FA\\u73B0\\u672A\\u77E5\\u95EE\\u9898\\uFF0C\\u63D0\\u9192\\u5931\\u8D25\\\",\\\"12\\\":\\\"\\u672A\\u542C\\u5B8C\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"13\\\":\\\"\\u4E0D\\u5728\\u670D\\u52A1\\u533A\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"14\\\":\\\"\\u5DF2\\u5173\\u673A\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"15\\\":\\\"\\u6B63\\u5728\\u901A\\u8BDD\\u4E2D\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"-1\\\":\\\"\\u7B49\\u5F85\\u63D0\\u9192\\u4E2D\\\",\\\"16\\\":\\\"\\u51FA\\u73B0\\u672A\\u77E5\\u95EE\\u9898\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"-2\\\":\\\"\\u91CD\\u590D\\u5185\\u5BB9\\uFF0C\\u53D1\\u9001\\u5931\\u8D25\\\",\\\"17\\\":\\\"\\u51FA\\u73B0\\u672A\\u77E5\\u95EE\\u9898\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"18\\\":\\\"\\u51FA\\u73B0\\u672A\\u77E5\\u95EE\\u9898\\uFF0C\\u63D0\\u9192\\u5931\\u8D25\\\",\\\"1\\\":\\\"\\u5E94\\u7528\\u5185\\u63D0\\u9192\\u4E2D...\\\",\\\"2\\\":\\\"\\u77ED\\u4FE1\\u63D0\\u9192\\u4E2D...\\\",\\\"3\\\":\\\"\\u7535\\u8BDD\\u63D0\\u9192\\u4E2D...\\\",\\\"4\\\":\\\"\\u51FA\\u73B0\\u672A\\u77E5\\u95EE\\u9898\\uFF0C\\u63D0\\u9192\\u5931\\u8D25\\\",\\\"5\\\":\\\"\\u7B49\\u5F85\\u63D0\\u9192\\u4E2D\\\",\\\"6\\\":\\\"VoIP\\u63D0\\u9192\\u4E2D...\\\",\\\"40\\\":\\\"\\u672A\\u63A5\\u542C\\uFF0C\\u5DF2\\u53D1\\u9001\\u5E94\\u7528\\u5185\\u63D0\\u9192\\\",\\\"30\\\":\\\"\\u5DF2\\u53D1\\u9001\\u5E94\\u7528\\u5185\\u63D0\\u9192\\\",\\\"20\\\":\\\"\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"31\\\":\\\"\\u5E94\\u7528\\u5185\\u63D0\\u9192\\u53D1\\u9001\\u5931\\u8D25\\\",\\\"10\\\":\\\"\\u672A\\u63A5\\u542C\\uFF0C\\u5DF2\\u53D1\\u9001\\u77ED\\u4FE1\\u63D0\\u9192\\\",\\\"21\\\":\\\"\\u63D0\\u9192\\u77ED\\u4FE1\\u53D1\\u9001\\u5931\\u8D25\\\",\\\"32\\\":\\\"\\u5BF9\\u65B9\\u672A\\u5B89\\u88C5\\u9489\\u9489\\uFF0C\\u63D0\\u9192\\u5931\\u8D25\\\"}\"}";
        add(get_confirm_status_info_data);


        String get_prefer_biz_call_num_data = "{\"headers\":{\"dt\":\"j\",\"mid\":\"0ef3001f 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":{\"result\":{\"code\":200,\"cause\":\"\\u6210\\u529F\"},\"isSupport\":false,\"numInfoList\":[],\"callType\":7}}";
        add(get_prefer_biz_call_num_data);

        String create_temp_session_info_data = "{\"headers\":{\"dt\":\"j\",\"mid\":\"2a7d0020 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":\"t-21fad179-7b77b9ecc8-2127ad1e-7d3b91-66658854-b50059dd-94d2-489a-ac25-3327fa3aef1f\"}";
        add(create_temp_session_info_data);

        String get_by_id_un_limited_response_data_164902 = "{\"headers\":{\"dt\":\"j\",\"mid\":\"c89c0024 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":{\"baseConversation\":{\"notificationOff\":0,\"superGroup\":0,\"unreadPoint\":0,\"extension\":{\"u_name_locale\":\"{\\\"en_US\\\":\\\"DingTalk Call\\\",\\\"ja_JP\\\":\\\"DingTalk\\u96FB\\u8A71\\\",\\\"zh_CN\\\":\\\"\\u9489\\u9489\\u7535\\u8BDD\\\",\\\"zh_TW\\\":\\\"\\u91D8\\u91D8\\u96FB\\u8A71\\\"}\",\"scheme\":\"https://qr.dingtalk.com/page/confenencelist\",\"lippiVideoConfMgrConfExpire_s\":\"1628084807\",\"lippiVideoConfMgrConfStatus\":\"0\",\"type\":\"3\",\"lippiVideoConfMgrOnGoingConfList\":\"{\\\"onGoingConfList\\\":[]}\"},\"showHistoryType\":0,\"gmtModified\":1629453656,\"entranceId\":0,\"banWordsTime\":0,\"notificationSound\":\"\",\"icon\":\"\",\"type\":1,\"title\":\"\",\"ownerId\":0,\"groupValidationInfo\":{\"type\":0},\"createAt\":1548218562672,\"memberVersion\":0,\"atAllType\":0,\"tag\":1,\"iconOption\":{},\"lastMsgCreateAt\":1629430510250,\"inBanWhite\":0,\"searchableModel\":{\"groupIdSearchable\":0,\"titleSearchable\":0,\"groupId\":0},\"conversationId\":\"164902:570085753\",\"memberCount\":2,\"active\":0,\"sort\":0,\"nodeType\":0,\"convVersion\":1628077607165,\"parentId\":\"\",\"memberLimit\":0,\"inBanBlack\":0,\"authority\":0,\"banWordsType\":0,\"categoryId\":0,\"status\":1},\"lastMessages\":[{\"receiverMessageStatus\":{\"readStatus\":0},\"senderMessageStatus\":{\"unReadCount\":1,\"totalCount\":1},\"baseMessage\":{\"memberTag\":0,\"createdAt\":1629430510250,\"extension\":{\"lastTime\":\"0\",\"recordId\":\"1629430510044\",\"targetNicks\":\"\\u5C0F\\u9B3C\",\"targetUids\":\"294191351\",\"callerNick\":\"\\u5C0F\\u9B3C\",\"beginTime\":\"1629430473\",\"callerId\":\"294191351\",\"sessionId\":\"611f22c83d70ea014f1436c0\",\"callType\":\"2\",\"status\":\"0\"},\"senderId\":0,\"creatorType\":1,\"conversationId\":\"164902:570085753\",\"messageId\":8003072570244,\"tag\":0,\"type\":1,\"openIdEx\":{\"openId\":164902,\"tag\":0},\"content\":{\"textContent\":{\"text\":\"\\u672A\\u63A5\\u6765\\u7535\\uFF1A\\u5C0F\\u9B3C\"},\"contentType\":1},\"recallStatus\":0}}]}}";
        add(get_by_id_un_limited_response_data_164902);

        String get_by_id_un_limited_response_data_21000 = "{\"headers\":{\"dt\":\"j\",\"mid\":\"ac290025 0\",\"sid\":\"0b0ac0126124cb3e3567509a5a0919a8d0b39b232559\"},\"code\":200,\"body\":{\"baseConversation\":{\"notificationOff\":1,\"superGroup\":0,\"unreadPoint\":0,\"extension\":{\"unread_count_type\":\"1\",\"u_name_locale\":\"{\\\"en_US\\\":\\\"Ding Drive\\\",\\\"ja_JP\\\":\\\"DingTalk\\u30AF\\u30E9\\u30A6\\u30C9\\\",\\\"zh_CN\\\":\\\"\\u9489\\u76D8\\\",\\\"zh_TW\\\":\\\"\\u91D8\\u76E4\\\"}\",\"scheme\":\"https://qr.dingtalk.com/page/yunpan\"},\"showHistoryType\":0,\"gmtModified\":1629717664,\"entranceId\":0,\"banWordsTime\":0,\"notificationSound\":\"\",\"icon\":\"\",\"type\":1,\"title\":\"\",\"ownerId\":0,\"groupValidationInfo\":{\"type\":0},\"createAt\":1548229770169,\"memberVersion\":0,\"atAllType\":0,\"tag\":6,\"iconOption\":{},\"lastMsgCreateAt\":1629717664190,\"inBanWhite\":0,\"searchableModel\":{\"groupIdSearchable\":0,\"titleSearchable\":0,\"groupId\":0},\"conversationId\":\"21000:570085753\",\"memberCount\":2,\"active\":0,\"sort\":0,\"nodeType\":0,\"convVersion\":1611555847799,\"parentId\":\"\",\"memberLimit\":0,\"inBanBlack\":0,\"authority\":0,\"banWordsType\":0,\"categoryId\":0,\"status\":1},\"lastMessages\":[{\"receiverMessageStatus\":{\"readStatus\":0},\"senderMessageStatus\":{\"unReadCount\":1,\"totalCount\":1},\"baseMessage\":{\"memberTag\":0,\"createdAt\":1629717664190,\"extension\":{\"dingpan_message_tag\":\"1\"},\"senderId\":0,\"creatorType\":2,\"conversationId\":\"21000:570085753\",\"messageId\":8029072012326,\"tag\":0,\"type\":1,\"openIdEx\":{\"openId\":21000,\"tag\":0},\"content\":{\"textContent\":{\"text\":\"\\u50A8\\u9662\\u8BDA\\u5728\\\"\\u8054\\u76DF\\u5E02\\u573A\\u5316\\u9879\\u76EE\\\"\\u4E0A\\u4F20\\u4E861\\u4E2A\\u6587\\u4EF6\"},\"contentType\":1},\"recallStatus\":0}}]}}";
        add(get_by_id_un_limited_response_data_21000);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/wty/project/play_code/src/main/java/com/opencode/dingding/entity/response/websocket/list_messages/aaa.txt")));
            StringBuilder buff = new StringBuilder();
            String data;
            while ((data = br.readLine()) != null) {
                buff.append(data);
            }

            String list_messages_data = UnicodeUtil.ascii2native(buff.toString());
            add(list_messages_data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }};

    static void before() {
        DingResponseRouteUtil.CALL_BACK_MAP.put("64b1c9da 0", RegResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("a5ba001b 0", SubscribeResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("3a72001d 0", GetSwitchStatusResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("00860022 0", LogResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("28fa001e 0", GetConfirmStatusInfoResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("0ef3001f 0", GetPreferBizCallNumResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("2a7d0020 0", CreateTempSessionInfoResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("c89c0024 0", GetByIdUnlimitedResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("ac290025 0", GetByIdUnlimitedResponse.class);
        DingResponseRouteUtil.CALL_BACK_MAP.put("0b15002b 0", ListMessagesResponse.class);
    }

    static void test() {

        before();

        for (String data : datas) {
            ResponseContext context = new ResponseContext();
            context.setRes(data);
            context.setMessage(new CheckLoginMessage());
            managerMsg(context);
        }
    }

    public static void managerMsg(ResponseContext context){
        for (AbstractResponseRoute handle : handles) {
            Class<WebSocketResponse> cls;
            if ((cls = handle.support(context)) != null) {
                WebSocketResponse response = handle.route(context, cls);
                System.out.println(response);
                System.out.println(context.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        ResponseManager.test();

    }

}
