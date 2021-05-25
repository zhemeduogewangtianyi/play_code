package com.opencode.shuma.interfaces;

import com.opencode.shuma.entity.Person;
import com.opencode.shuma.entity.Skill;
import com.opencode.shuma.exception.BabyBizException;

/**
 * 修改宠物属性接口
 * */
public interface UpdateDigitalMonsterInterface {

    /** 增加技能 */
    boolean addSkill(Person person, Skill skill) throws BabyBizException;

    /** 减少技能 */
    boolean delSkill(Person person, Skill skill) throws BabyBizException;

    /** 增加生命 */
    Integer addHealth(Person person , Integer health) throws BabyBizException;

    /** 减少生命 */
    Integer subHealth(Person person , Integer health) throws BabyBizException;

    /** 增加魔法 */
    Integer addMana(Person person , Integer mana) throws BabyBizException;

    /** 减少魔法 */
    Integer subMana(Person person , Integer mana) throws BabyBizException;

    /** 增加愤怒 */
    Integer addAnger(Person person , Integer anger) throws BabyBizException;

    /** 减少愤怒 */
    Integer subAnger(Person person , Integer anger) throws BabyBizException;

    /** 增加经验 */
    boolean addExperience(Person person , Long experience) throws BabyBizException;

    /** 减少经验 */
    boolean subExperience(Person person , Integer experience);

    /** 增加速度 */
    boolean addSpeed(Person person,Integer speed);

    /** 减少速度 */
    boolean subSpeed(Person person,Integer speed);

    /** 增加物理防御 */
    boolean addPhysicalDefense(Person person , Integer physicalDefense);

    /** 减少物理防御 */
    boolean subPhysicalDefense(Person person , Integer physicalDefense);

    /** 增加魔法防御 */
    boolean addMagicDefense(Person person , Integer magicDefense);

    /** 减少魔法防御 */
    boolean subMagicDefense(Person person , Integer magicDefense);

}
