package com.opencode.code.answer.signin.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BitSetManager {

    public static int countDay(int currentBitSet){

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

    public static int megerBitSet(Integer bitSet){
        //合并新老记录
        return (1 | (1 << bitSet)) >> 1;
    }

    public static Integer getBitSetDay(String ymd) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ymd));
        //今天几号
        return calendar.get(Calendar.DAY_OF_MONTH);

    }

    public static Integer getBitSetMonth(String ymd) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ymd));
        //今天几月
        return calendar.get(Calendar.MONTH) + 1;

    }

    public static Integer getBitSetYear(String ymd) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ymd));
        //今夕何年
        return calendar.get(Calendar.YEAR);

    }

}
