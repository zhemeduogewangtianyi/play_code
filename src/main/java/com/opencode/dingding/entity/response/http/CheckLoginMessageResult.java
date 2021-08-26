package com.opencode.dingding.entity.response.http;

import lombok.Data;

@Data
public class CheckLoginMessageResult {

    private String errorMsg;
    private String errorCode;
    private boolean success;
    private String result;

}
