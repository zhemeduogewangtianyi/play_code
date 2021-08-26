package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class Dept {

    private String deptName;
    private Boolean unionNode;
    private Integer unionNodeType;
    private Boolean isDelete;
    private Integer memberCount;
    private Long deptId;
    private Boolean unionRootNode;

    private DeptSetting deptSetting;

    private Long orgId;

    private List<Tag> tags;

}
