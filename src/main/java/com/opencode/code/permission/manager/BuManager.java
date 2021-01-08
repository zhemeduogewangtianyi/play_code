package com.opencode.code.permission.manager;

import com.opencode.code.permission.bean.BuDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class BuManager {

    private static Map<String, BuDO> BU_USER = new HashMap<>();

    public Map<String,BuDO> queryAll(){
        return BU_USER;
    }

    public boolean addBu(BuDO bucUser){
        String name = bucUser.getName();
        if(StringUtils.isEmpty(name)){
            return false;
        }
        BU_USER.put(name,bucUser);
        return true;
    }

    public boolean remove(String name){

        for(Iterator<Map.Entry<String,BuDO>> car = BU_USER.entrySet().iterator(); car.hasNext() ; ){

            Map.Entry<String, BuDO> next = car.next();
            if(next.getKey().equals(name)){
                car.remove();
            }
        }

        return true;
    }

    public BuDO queryByName(String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        return BU_USER.get(name);
    }

}
