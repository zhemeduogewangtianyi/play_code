package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileModel {

    //TODO []
    private List<Object> tagCodes;
    private String nickPinyin;
//    private UserProfileModelExtension extension;
    private String extension;
    private Integer numberType;
//    private UserProfileModelOrgInfo orgInfoStr;
    private String orgInfoStr;

    private List<AuthOrg> authOrgs;
    private Boolean isActive;
    private Integer type;
    private Boolean isDataComplete;
    private String nick;
    private Long uid;
    private List<Long> orgIdList;

    private OrgInfo orgInfo;
    private BusinessCardOrgInfo businessCardOrgInfo;

    private String uidCipher;

    private Settings settings;

    private Integer ver;
    private Boolean isOrgUser;
    private Long activeTime;
    private String mobile;

    //TODO []
    private List<Object> labels;
    private String avatarMediaId;
    private String name;
    private String stateCode;
    private Integer status;

}
