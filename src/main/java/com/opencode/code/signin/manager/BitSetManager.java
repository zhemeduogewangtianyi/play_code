package com.opencode.code.signin.manager;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class BitSetManager {

    public int countDay(int currentBitSet){

        String binaryBitSet = Integer.toBinaryString(currentBitSet);

        int day = 0;
        char[] chars = binaryBitSet.toCharArray();
        for(char c : chars){
            if(c == '1'){
                day ++;
            }
        }
        return day;
    }

    public int megerBitSet(Integer bitSet){
        //合并新老记录
        return (1 | (1 << bitSet)) >> 1;
    }

    public Integer getBitSetDay(String ymd) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ymd));
        //今天几号
        return calendar.get(Calendar.DAY_OF_MONTH);

    }

    public Integer getBitSetMonth(String ymd) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ymd));
        //今天几月
        return calendar.get(Calendar.MONTH) + 1;

    }

    public Integer getBitSetYear(String ymd) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ymd));
        //今夕何年
        return calendar.get(Calendar.YEAR);

    }

}
