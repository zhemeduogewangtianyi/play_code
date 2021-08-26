package com.opencode.dingding.entity.ding;

import lombok.Data;

@Data
public class OrgExtFieldModel {

    private Boolean orgSelfTag;
    private Boolean isSearch;
    private Boolean clientDisplay;
    private String name;
    private Long id;
    private Long orgId;
    private String itemKey;

    private Boolean required;

}
