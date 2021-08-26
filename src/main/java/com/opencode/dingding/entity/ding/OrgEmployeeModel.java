package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class OrgEmployeeModel {

    private String orgUserName;

    private String extension;

    private Integer ver;

    private String orgName;

    private Integer role;

    private List<Integer> roles;

    private String externalTitle;

    private String orgTitle;

    private Boolean hasSubordinate;

    private String orgUserMobile;

    private Long orgId;

    private String orgAuthEmail;

    //TODO []
    private List<Object> labels;

    private List<Tag> tags;

    private String uid;

    private String orgEmail;

    private String orgUserNamePinyin;

    private Boolean isMainOrg;

    private String stateCode;

    private List<Dept> depts;

    private String jobNumber;

    private String orgStaffId;

}
