package com.opencode.shuma;

import com.alibaba.fastjson.JSON;
import com.opencode.shuma.entity.BabyAttribute;
import com.opencode.shuma.entity.DigitalMonster;
import com.opencode.shuma.entity.Person;
import com.opencode.shuma.entity.Skill;
import com.opencode.shuma.enums.MedalEnum;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        Person person = new Person();
        person.setId(1L);
        person.setName("太一");
        person.setAge(9);
        person.setFlag(1);
        person.setGender(1);
        person.setHobby("踢足球");
        person.setMedalEnum(MedalEnum.COURAGE);
        person.setRemark("小学生");

        DigitalMonster dm = new DigitalMonster();
        dm.setId(1L);
        dm.setCreateTime(new Date());
        dm.setName("亚古兽");

        BabyAttribute ba = new BabyAttribute();
        ba.setLevel(1);
        ba.setMaxHealth(100);
        ba.setCurrentHealth(100);
        ba.setMaxMana(100);
        ba.setCurrentMana(100);
        ba.setMaxAnger(150);
        ba.setCurrentAnger(0);
        ba.setExperience(0L);
        ba.setTargetExperience(100L);

        dm.setBabyAttribute(ba);

        Skill sk1 = new Skill();
        sk1.setId(1L);
        sk1.setName("火球术");
        sk1.setRemark("发射一个小火球");
        sk1.setType(1);
        sk1.setHurt(10);
        sk1.setConsumer(10);


        person.setLink(dm);

        System.out.println(JSON.toJSONString(person));

    }

}
