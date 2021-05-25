package com.opencode.shuma.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 技能
 * */
@Getter
@Setter
public class Skill {

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 介绍 */
    private String remark;

    /** 类型 */
    private Integer type;

    /** 伤害 */
    private Integer hurt;

    /** 消耗 */
    private Integer consumer;

}
