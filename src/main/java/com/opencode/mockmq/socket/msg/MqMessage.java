package com.opencode.mockmq.socket.msg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MqMessage {

    private Integer type;

    private String content;

}
