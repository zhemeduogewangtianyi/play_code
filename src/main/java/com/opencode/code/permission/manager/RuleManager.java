package com.opencode.code.permission.manager;

import com.opencode.code.permission.bean.RuleDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class RuleManager {

    public static List<RuleDO> RULE_LIST = new ArrayList<>();

    static{
        RuleDO es = new RuleDO();
        es.setId(0L);
        es.setCode(1);
        es.setName("es模块");
        es.setFlag(0);

        RuleDO signIn = new RuleDO();
        signIn.setId(1L);
        signIn.setCode(2);
        signIn.setName("签到模块");
        signIn.setFlag(0);

        Collections.addAll(RULE_LIST,es,signIn);
    }

    public List<RuleDO> queryAll(){
        return RULE_LIST;
    }

    public RuleDO queryByCode(int code){
        for(RuleDO ruleDO : RULE_LIST){
            if(ruleDO.getCode() == code){
                return ruleDO;
            }
        }
        return null;
    }

    public Long addPermission(RuleDO ruleDO){
        long id = RULE_LIST.size();
        ruleDO.setId(id);
        RULE_LIST.add(ruleDO);
        return id;
    }

    public boolean remove(Long id){

        for(Iterator<RuleDO> car = RULE_LIST.iterator(); car.hasNext() ; ){
            RuleDO next = car.next();
            if(next.getId().equals(id)){
                car.remove();
            }
        }
        return true;
    }

}
