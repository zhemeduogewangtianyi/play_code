package com.opencode.dingding.entity;

import lombok.Data;

import java.util.List;

@Data
public class CardDynamicModel {

    //TODO []
    private List<Object> orgThemesNew;

    private String qrCode;

    private List<MyThemesNew> myThemesNew;

    /** 可以转换为 ThemeConfig */
    private List<String> orgThemes;

    private OrgThemeModel orgThemeModel;

    private Integer themeType;

    private Integer completeDegree;

    private String cardToken;

}
