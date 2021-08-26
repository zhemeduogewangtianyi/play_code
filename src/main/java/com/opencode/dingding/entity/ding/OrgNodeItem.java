package com.opencode.dingding.entity.ding;

import lombok.Data;

import java.util.List;

@Data
public class OrgNodeItem {

    private Dept dept;

    private Integer nodeType;

    private List<Dept> masterNodeList;

}
