package com.opencode.shuma.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 数码宝贝
 * */
@Getter
@Setter
public class DigitalMonster {

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 出生日期 */
    private Date createTime;

    /** 类型 */
    private Integer type;

    /** 技能 */
    private List<Skill> skills;

    /** 宠物属性 */
    private BabyAttribute babyAttribute;

    /** 下一个形态 */
    private DigitalMonster nextForm;


}
