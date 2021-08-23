package com.opencode.dingding.entity;

import lombok.Data;

@Data
public class CardSettingModel {

    private Boolean addCustomer;
    private Boolean titleVisiableForStranger;
    private Boolean emailVisiableForStranger;
    private Boolean mobileVisiableForStranger;
    private Boolean addressVisiableForStranger;
    private Boolean orgVisiableForStranger;
    private Boolean receiveCardMessage;

}
