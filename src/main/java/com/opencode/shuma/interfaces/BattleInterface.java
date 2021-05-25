package com.opencode.shuma.interfaces;

import com.opencode.shuma.entity.Person;
import com.opencode.shuma.entity.Skill;

/**
 * 战斗接口
 * */
public interface BattleInterface {

    Skill attack(Person person ,Skill initiatorSkill);

    Person defense(Person person);

}
