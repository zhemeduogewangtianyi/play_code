package com.opencode.dingding.entity.ding;

import lombok.Data;

@Data
public class UserPermissionModel {

    private boolean canBeSendFriendRequest;
    private boolean canBeSentMsg;
    private boolean couldShowMobile;
    private boolean canBeSendDing;
    private boolean couldCreateOrg;
    private boolean canBeSendConference;

}
