package com.opencode.shuma.enums;

public enum SkillTypeEnum {

    MAGIC_ATTACK(0,"魔法攻击"),
    PHYSICAL_ATTACK(1,"物理攻击"),
    ADD_BLOOD(2,"加血"),
    ADD_PHYSICAL_DEFENSE(3,"增加物理防御"),
    ADD_MAGIC_DEFENSE(4,"增加魔法防御"),
    SUB_PHYSICAL_DEFENSE(5,"减少魔法防御"),
    SUB_MAGIC_DEFENSE(6,"减少魔法防御"),
    ADD_SPEED(7,"增加速度"),
    SUB_SPEED(8,"减少速度"),
    ADD_ANGER(9,"增加愤怒"),
    SUB_ANGER(10,"减少速度"),

    ;

    private Integer code;

    private String value;

    SkillTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
