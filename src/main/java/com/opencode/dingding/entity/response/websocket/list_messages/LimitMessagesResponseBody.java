package com.opencode.dingding.entity.response.websocket.list_messages;

import com.opencode.dingding.entity.response.websocket.body.lastmessage.ReceiverMessageStatus;
import com.opencode.dingding.entity.response.websocket.body.lastmessage.SenderMessageStatus;
import com.opencode.dingding.entity.response.websocket.body.lastmessage.basemessage.LastMessageBaseMessage;
import lombok.Data;


@Data
public class LimitMessagesResponseBody {

    private ReceiverMessageStatus receiverMessageStatus;
    private SenderMessageStatus senderMessageStatus;
    private LastMessageBaseMessage baseMessage;

}
