package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileExtensionModel {

    private boolean isEmailComplete;

    private UserPermissionModel userPermissionModel;

    private List<OrgEmployee> orgEmployees;

    private UserProfileModel userProfileModel;

    private Boolean allowChangeDingTalkId;

    private Boolean partial;

    private SpecialOrgUserModel specialOrgUserModel;

    private CardUserModel cardUserModel;

}
