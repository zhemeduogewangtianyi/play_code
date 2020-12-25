package com.opencode.code.signin.manager;

import com.opencode.code.signin.bean.SignInContext;
import com.opencode.code.signin.cache.SignInCacheStructure;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TestSignInManager {

    /** 今日是否签到 */
    public boolean toDayIsSignInBatch(String type, String username, Date date) throws ParseException {

        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(date);
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

    /** 签到 */
    public Object signInBatch(String type,String username,Date date) throws ParseException {

        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String yearKey = BitSetManager.getBitSetYear(ymd).toString();
        String monthKey = BitSetManager.getBitSetMonth(ymd).toString();

        if(this.toDayIsSignInBatch(type,username,date)){
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

}
