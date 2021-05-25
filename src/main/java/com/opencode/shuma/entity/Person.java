package com.opencode.shuma.entity;

import com.opencode.shuma.enums.MedalEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 人类
 * */
@Getter
@Setter
public class Person {

    /** 序号 */
    private Long id;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 0：女 1：男 */
    private Integer gender;

    /** 爱好 */
    private String hobby;

    /** 介绍 */
    private String remark;

    /** 勋章 */
    private MedalEnum medalEnum;

    /** 有效标志 */
    private Integer flag;

    /** 连接宠物 */
    private DigitalMonster link;

}
