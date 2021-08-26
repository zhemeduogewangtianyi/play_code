package com.opencode.dingding.entity.response.websocket.get_by_id_un_limited;

import com.opencode.dingding.entity.response.websocket.body.baseconversation.BaseConversation;
import com.opencode.dingding.entity.response.websocket.body.lastmessage.LastMessage;
import lombok.Data;

import java.util.List;


@Data
public class GetByIdUnlimitedResponseBody {

    private BaseConversation baseConversation;
    private List<LastMessage> lastMessages;

}
