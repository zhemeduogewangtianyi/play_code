package com.opencode.dingding.entity.response.websocket.body.baseconversation;

import com.opencode.dingding.entity.response.websocket.body.GroupValidationInfo;
import com.opencode.dingding.entity.response.websocket.body.IconOption;
import lombok.Data;

@Data
public class BaseConversation {

    private Integer notificationOff;
    private Integer superGroup;
    private Integer unreadPoint;
    private BaseConversationExtension extension;
    private Integer showHistoryType;
    private Long gmtModified;
    private Integer entranceId;
    private Integer banWordsTime;
    private String notificationSound;
    private String icon;
    private Integer type;
    private String title;
    private Long ownerId;
    private GroupValidationInfo groupValidationInfo;
    private Long createAt;
    private Integer memberVersion;
    private Integer atAllType;
    private Integer tag;
    private IconOption iconOption;
    private Long lastMsgCreateAt;
    private Integer inBanWhite;
    private SearchableModel searchableModel;
    private String conversationId;
    private Integer memberCount;
    private Integer active;
    private Long sort;
    private Integer nodeType;
    private Long convVersion;
    private String parentId;
    private Integer memberLimit;
    private Integer inBanBlack;
    private Integer authority;
    private Integer banWordsType;
    private Integer categoryId;
    private Integer status;

}
