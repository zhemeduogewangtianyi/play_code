package com.opencode.dingding.entity;

import lombok.Data;

@Data
public class CardProfileModel {

    private CardSettingModel cardSettingModel;
    private Long uid;
    private CardExtensionModel cardExtensionModel;
    private CardDynamicModel cardDynamicModel;
    private CardStyleModel cardStyleModel;

}
