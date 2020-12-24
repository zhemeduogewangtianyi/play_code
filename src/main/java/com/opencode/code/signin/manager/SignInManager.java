package com.opencode.code.signin.manager;

import com.opencode.code.signin.bean.SignInContext;
import com.opencode.code.signin.cache.SignInCacheStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SignInManager {

    @Autowired
    private BitSetManager bitSetManager;

    /** 签到 */
    public Object signIn(String type,String username) throws ParseException {

        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yearKey = bitSetManager.getBitSetYear(ymd).toString();
        String monthKey = bitSetManager.getBitSetMonth(ymd).toString();

        if(this.toDayIsSignIn(type,username)){
            return "今天已打卡";
        }else{

            SignInContext signInContext = new SignInContext();
            signInContext.setType(type);
            signInContext.setUsername(username);
            signInContext.setYear(yearKey);
            signInContext.setMonth(monthKey);

            Integer cacheCount = SignInCacheStructure.getCacheCount(signInContext);

            Integer bitSet = bitSetManager.getBitSetDay(ymd);
            int meger = bitSetManager.megerBitSet(bitSet);
            int currentBitSet = cacheCount | meger;

            int day = bitSetManager.countDay(currentBitSet);

            signInContext.setCount(currentBitSet);
            SignInCacheStructure.cacheCount(signInContext);

            return "打卡成功，累计打卡 " + day + " 天";
        }
    }

    /** 今日是否签到 */
    public boolean toDayIsSignIn(String type,String username) throws ParseException {

        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yearKey = bitSetManager.getBitSetYear(ymd).toString();
        String monthKey = bitSetManager.getBitSetMonth(ymd).toString();

        SignInContext signInContext = new SignInContext();
        signInContext.setType(type);
        signInContext.setUsername(username);
        signInContext.setYear(yearKey);
        signInContext.setMonth(monthKey);

        Integer cacheCount = SignInCacheStructure.getCacheCount(signInContext);

        Integer bitSet = bitSetManager.getBitSetDay(ymd);
        int i1 = cacheCount >> (bitSet - 1);
        //当天放在最低位
        return (i1 & 1) != 0;
    }

}
