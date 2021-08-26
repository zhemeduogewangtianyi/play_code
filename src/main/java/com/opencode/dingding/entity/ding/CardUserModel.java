package com.opencode.dingding.entity.ding;

import lombok.Data;

@Data
public class CardUserModel {

    private String avatarMediaId;
    private Integer friendStatus;
    private Long uid;
    private Boolean myFriend;
    private String name;

    private CardProfileModel cardProfileModel;

    private String encodeUid;

}
