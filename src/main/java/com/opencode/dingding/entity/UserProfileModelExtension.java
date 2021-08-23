package com.opencode.dingding.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserProfileModelExtension {

    private List<String> bpmsHandSign;
    private Map<String,UserProfileModelExtensionExpireTag> expireTag;
    //TODO []
    private List<Object> ownnessl;
    private String locale;

}
