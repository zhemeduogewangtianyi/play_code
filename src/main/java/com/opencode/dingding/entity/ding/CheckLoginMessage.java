package com.opencode.dingding.entity.ding;

import lombok.Data;

@Data
public class CheckLoginMessage {

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
    private String did;
    private String sid;
    private String uid;

    private UserProfileExtensionModel userProfileExtensionModel;

    private String refreshToken;



}
