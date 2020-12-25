package com.opencode.code.signin.manager;

import com.opencode.code.signin.bean.SignInContext;
import com.opencode.code.signin.cache.SignInCacheStructure;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Component
public class SignInManager {

    /** 签到 */
    public Object signIn(String type,String username) throws ParseException {

        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yearKey = BitSetManager.getBitSetYear(ymd).toString();
        String monthKey = BitSetManager.getBitSetMonth(ymd).toString();

        if(this.toDayIsSignIn(type,username)){
            return "今天已打卡";
        }else{

            SignInContext signInContext = new SignInContext();
            signInContext.setType(type);
            signInContext.setUsername(username);
            signInContext.setYear(yearKey);
            signInContext.setMonth(monthKey);

            Integer cacheCount = SignInCacheStructure.getCacheCount(signInContext);

            Integer bitSet = BitSetManager.getBitSetDay(ymd);
            int meger = BitSetManager.megerBitSet(bitSet);
            int currentBitSet = cacheCount | meger;

            int day = BitSetManager.countDay(currentBitSet);

            signInContext.setBitSet(currentBitSet);
            SignInCacheStructure.cacheCount(signInContext);

            return "打卡成功，累计打卡 " + day + " 天";
        }
    }

    /** 今日是否签到 */
    public boolean toDayIsSignIn(String type,String username) throws ParseException {

        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yearKey = BitSetManager.getBitSetYear(ymd).toString();
        String monthKey = BitSetManager.getBitSetMonth(ymd).toString();

        SignInContext signInContext = new SignInContext();
        signInContext.setType(type);
        signInContext.setUsername(username);
        signInContext.setYear(yearKey);
        signInContext.setMonth(monthKey);

        Integer cacheCount = SignInCacheStructure.getCacheCount(signInContext);

        Integer bitSet = BitSetManager.getBitSetDay(ymd);
        int i1 = cacheCount >> (bitSet - 1);
        //当天放在最低位
        return (i1 & 1) != 0;
    }

    public Integer getSignInCount(String type, String name) throws ParseException {
        Map<String, Map<String, Map<String, Map<String, SignInContext>>>> typeMapInfo = SignInCacheStructure.getTypeMapInfo();
        Map<String, Map<String, Map<String, SignInContext>>> nameMap = typeMapInfo.get(type);
        if(nameMap == null){
            return -1;
        }
        Integer count = 0;
        Map<String, Map<String, SignInContext>> yearMap = nameMap.get(name);
        if(yearMap != null){
            for(Iterator<Map.Entry<String,Map<String,SignInContext>>> monthCar = yearMap.entrySet().iterator() ; monthCar.hasNext() ; ){
                Map.Entry<String, Map<String, SignInContext>> monthNext = monthCar.next();
                Map<String, SignInContext> monthMap = monthNext.getValue();
                for(Iterator<Map.Entry<String, SignInContext>> bitSetCar = monthMap.entrySet().iterator() ; bitSetCar.hasNext() ;){
                    Map.Entry<String, SignInContext> next = bitSetCar.next();
                    SignInContext sc = next.getValue();
                    count += sc.getCountDay();
                }

            }
        }

        return count;
    }

}
