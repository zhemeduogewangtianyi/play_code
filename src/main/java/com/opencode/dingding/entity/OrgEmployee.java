package com.opencode.dingding.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrgEmployee {

    private SuperUserSettingModel superUserSettingModel;

    private List<OrgNodeItem> OrgNodeItemList;

    private String orgWorkAddress;

    private Integer orgLevel;

    private String remark;

    private Boolean isAdmin;

    private EmpSettingModel empSettingModel;

    private Long spaceId;

    private String extNumber;

    private OrgDetail orgDetail;

    private Boolean isOrgAuth;

    private OrgResourcePermissions orgResourcePermissions;

    private OrgEmployeeModel orgEmployeeModel;

    private List<OrgExtProperty> orgExtPropertyList;



}
