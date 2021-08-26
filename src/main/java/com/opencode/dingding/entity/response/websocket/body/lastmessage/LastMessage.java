package com.opencode.dingding.entity.response.websocket.body.lastmessage;

import com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage.LastMessageBaseMessage;
import lombok.Data;

@Data
public class LastMessage {

    private ReceiverMessageStatus receiverMessageStatus;
    private SenderMessageStatus senderMessageStatus;
    private LastMessageBaseMessage baseMessage;

}
