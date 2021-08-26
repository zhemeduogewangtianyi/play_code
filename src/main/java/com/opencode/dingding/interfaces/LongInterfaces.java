package com.opencode.dingding.interfaces;

import com.opencode.dingding.enums.DingConfigTopicEnum;
import com.opencode.dingding.enums.UnlimitedTypeEnum;

public interface LongInterfaces {

    void reg();

    void subscribe();

    void getSwitchStatus();

    void log();

    void getConfirmStatusInfo();

    void getPreferBizCallNum();

    void createTempSessionInfo();

    void getByIdUnlimited(UnlimitedTypeEnum unlimitedTypeEnum);

    void getConf(DingConfigTopicEnum topicEnum);

    void getUserProfileByUids();

    void queryUserDingMailStatus();

    void listNewest();

    void getState();

    void IDLSend(String conversationId,String text);

    void getUserProfileExtensionByUid();

    void listMembers(String conversationId);

    void listMessages(String conversationId);

    void getUserEmployeeInfos(String[] userId,String orgId);

}
