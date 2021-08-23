package com.opencode.dingding.entity;

import lombok.Data;

@Data
public class AuthOrg {

    private Boolean authFromB2b;
    private Integer vipLevel;
    private Boolean thirdPartyEncrypt;
    private String orgName;
    private String encryptedOrgId;
    private String corpId;
    private Integer authLevel;
    private String logoMediaId;
    private Long orgId;

}
