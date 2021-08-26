package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class OrgDetail {

    private String ext;

    private String orgName;

    private String corpId;

    private Integer rightsLevel;

    private OrgOA orgOA;

    private Boolean managePermission;

    private Integer orgCapacity;

    private String industryDesc;

    private Boolean leavePermission;

    private Long orgId;

    private Integer industryCode;

    private List<Tag> tags;

    private Long uid;

    private Long spaceId;

    private OrganizationSettingsModel organizationSettingsModel;

    private Integer authLevel;

    private String logoMediaId;

}
