package com.opencode.dingding.entity;

import lombok.Data;

@Data
public class CheckLoginMessage {

    private String errorMsg;
    private String errorCode;
    private boolean success;

    private String nickPinyin;
    private boolean syncProtocol;
    private String tokenId;
    private boolean inActiveToActive;
    private boolean loginByTmpPwd;
    private String secretToken;
    private String accessToken;
    private Long expiredTime;
    private String nick;
    private String domain;
    private String appKey;
    private boolean realm;
    private String timestamp;

    private String tmpCode;
    private Long openId;

    private UserProfileExtensionModel userProfileExtensionModel;

    private String refreshToken;



}
