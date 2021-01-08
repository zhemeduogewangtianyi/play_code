package com.opencode.code.permission.manager;

import com.opencode.code.permission.bean.BucUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class BucManager {

    private static final Map<String, BucUser> BUC_USER = new HashMap<>();

    public Map<String,BucUser> queryAll(){
        return BUC_USER;
    }

    public boolean addUser(BucUser bucUser){
        String workNumber = bucUser.getWorkNumber();
        if(StringUtils.isEmpty(workNumber)){
            return false;
        }
        BUC_USER.put(workNumber,bucUser);
        return true;
    }

    public boolean remove(String workNumber){

        //noinspection Java8CollectionRemoveIf
        for(Iterator<Map.Entry<String,BucUser>> car = BUC_USER.entrySet().iterator() ; car.hasNext() ; ){

            Map.Entry<String, BucUser> next = car.next();
            if(next.getKey().equals(workNumber)){
                car.remove();
            }
        }

        return true;
    }

    public BucUser queryByNumber(String workNumber){
        if(StringUtils.isEmpty(workNumber)){
            return null;
        }
        return BUC_USER.get(workNumber);
    }

}
