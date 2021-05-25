package com.opencode.shuma.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 宠物属性
 * */
@Getter
@Setter
public class BabyAttribute {

    /** 最大生命 */
    private Integer maxHealth;

    /** 当前生命 */
    private Integer currentHealth;

    /** 最大魔法 */
    private Integer maxMana;

    /** 当前魔法 */
    private Integer currentMana;

    /** 最大愤怒值 */
    private Integer maxAnger;

    /** 当前愤怒值 */
    private Integer currentAnger;

    /** 速度 */
    private Integer speed;

    /** 物理防御 */
    private Integer physicalDefense;

    /** 魔法防御 */
    private Integer magicDefense;

    /** 等级 */
    private Integer level;

    /** 目标经验 */
    private Long targetExperience;

    /** 经验 */
    private Long experience;

}
