package com.opencode.shuma.interfaces.impl;

import com.opencode.shuma.entity.BabyAttribute;
import com.opencode.shuma.entity.DigitalMonster;
import com.opencode.shuma.entity.Person;
import com.opencode.shuma.entity.Skill;
import com.opencode.shuma.exception.BabyBizException;
import com.opencode.shuma.interfaces.UpdateDigitalMonsterInterface;

import java.util.ArrayList;
import java.util.List;

public class DefaultUpdateDigitalMonsterInterfaceImpl implements UpdateDigitalMonsterInterface {

    @Override
    public boolean addSkill(Person person, Skill skill) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        List<Skill> skills = dm.getSkills();
        if(skills == null){
            skills = new ArrayList<>();
        }
        if(skills.size() == 10){
            throw new BabyBizException("宠物已经有了10个技能了");
        }
        skills.add(skill);
        return true;
    }

    @Override
    public boolean delSkill(Person person, Skill skill) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        List<Skill> skills = dm.getSkills();
        if(skills == null || skills.size() == 0){
            throw new BabyBizException("宠物没有技能");
        }
        skills.remove(skill);
        return true;
    }

    @Override
    public Integer addHealth(Person person, Integer health) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        Integer maxHealth = babyAttribute.getMaxHealth();
        Integer currentHealth = babyAttribute.getCurrentHealth();

        int i = currentHealth + health;
        int settingHealth ;
        if(maxHealth.compareTo(i) < 0){
            settingHealth = i - maxHealth;
        }else if(maxHealth.compareTo(i) == 0){
            return 0;
        }else{
            settingHealth = i;
        }

        babyAttribute.setCurrentHealth(settingHealth);
        return settingHealth;
    }

    @Override
    public Integer subHealth(Person person, Integer health) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        Integer currentHealth = babyAttribute.getCurrentHealth();

        int settingsHealth = 0;
        if(currentHealth.compareTo(health) < 0){
            settingsHealth = currentHealth - health;
        }

        babyAttribute.setCurrentHealth(settingsHealth);

        return health;
    }

    @Override
    public Integer addMana(Person person, Integer mana) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        Integer maxMana = babyAttribute.getMaxMana();
        Integer currentMana = babyAttribute.getCurrentMana();

        int i = currentMana + mana;
        int settingHealth ;
        if(maxMana.compareTo(i) < 0){
            settingHealth = i - maxMana;
        }else if(maxMana.compareTo(i) == 0){
            return 0;
        }else{
            settingHealth = i;
        }

        babyAttribute.setCurrentHealth(settingHealth);
        return settingHealth;
    }

    @Override
    public Integer subMana(Person person, Integer mana) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        Integer currentMana = babyAttribute.getCurrentMana();

        int settingsHealth = 0;
        if(currentMana.compareTo(mana) < 0){
            settingsHealth = currentMana - mana;
        }

        babyAttribute.setCurrentHealth(settingsHealth);

        return mana;
    }

    @Override
    public Integer addAnger(Person person, Integer anger) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        Integer maxAnger = babyAttribute.getMaxAnger();
        Integer currentAnger = babyAttribute.getCurrentAnger();

        int i = currentAnger + anger;
        int settingHealth ;
        if(maxAnger.compareTo(i) < 0){
            settingHealth = i - maxAnger;
        }else if(maxAnger.compareTo(i) == 0){
            return 0;
        }else{
            settingHealth = i;
        }

        babyAttribute.setCurrentHealth(settingHealth);
        return settingHealth;
    }

    @Override
    public Integer subAnger(Person person, Integer anger) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        Integer currentAnger = babyAttribute.getCurrentAnger();

        int settingsHealth = 0;
        if(currentAnger.compareTo(anger) < 0){
            settingsHealth = currentAnger - anger;
        }

        babyAttribute.setCurrentHealth(settingsHealth);

        return anger;
    }

    @Override
    public boolean addExperience(Person person, Long experience) throws BabyBizException {
        DigitalMonster dm = checkDm(person);
        BabyAttribute babyAttribute = checkAttribute(dm);
        babyAttribute.setExperience(experience);
        Long targetExperience = babyAttribute.getTargetExperience();
        if(targetExperience.compareTo(babyAttribute.getExperience()) == 0){
            babyAttribute.setLevel(babyAttribute.getLevel() + 1);
        }
        return true;
    }

    @Override
    public boolean subExperience(Person person, Integer experience) {
        return true;
    }

    @Override
    public boolean addSpeed(Person person, Integer speed) {
        return true;
    }

    @Override
    public boolean subSpeed(Person person, Integer speed) {
        return true;
    }

    @Override
    public boolean addPhysicalDefense(Person person, Integer physicalDefense) {
        return true;
    }

    @Override
    public boolean subPhysicalDefense(Person person, Integer physicalDefense) {
        return true;
    }

    @Override
    public boolean addMagicDefense(Person person, Integer magicDefense) {
        return true;
    }

    @Override
    public boolean subMagicDefense(Person person, Integer magicDefense) {
        return true;
    }

    private DigitalMonster checkDm(Person person) throws BabyBizException {
        DigitalMonster link = person.getLink();
        if(link == null){
            throw new BabyBizException("宠物不存在");
        }
        return link;
    }

    private BabyAttribute checkAttribute(DigitalMonster dm) throws BabyBizException {
        BabyAttribute babyAttribute = dm.getBabyAttribute();
        if(babyAttribute == null){
            throw new BabyBizException("属性不存在");
        }
        return babyAttribute;
    }
}
