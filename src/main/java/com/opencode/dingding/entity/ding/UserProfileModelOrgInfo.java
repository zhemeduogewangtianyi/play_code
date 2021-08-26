package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileModelOrgInfo {

    private Integer mainOrgAuthLevel;
    private String mainOrgCorpId;
    private Long mainOrgId;
    private String mainOrgLogo;
    private String mainOrgName;
    private String mainOrgToken;
    private List<String> orgIdList;
}
