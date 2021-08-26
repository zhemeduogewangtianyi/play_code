package com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage;

import com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage.content.LastMessageBaseMessageContent;
import com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage.extension.BaseMessageExtension;
import lombok.Data;

@Data
public class LastMessageBaseMessage {

    private Integer memberTag;
    private Long createdAt;
    private BaseMessageExtension extension;
    private Integer senderId;
    private Integer creatorType;
    private String conversationId;
    private Long messageId;
    private Integer tag;
    private Integer type;
    private OpenIdEx openIdEx;
    private LastMessageBaseMessageContent content;
    private Integer recallStatus;

}
