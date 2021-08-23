package com.opencode.dingding.entity;

import lombok.Data;

@Data
public class CardStyleModel {

    private String orgThemeId;
    private Boolean canChange;
    private String theme;
    private ThemeConfig themeConfig;

}
