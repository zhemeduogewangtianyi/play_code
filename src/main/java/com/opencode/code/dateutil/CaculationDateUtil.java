package com.opencode.code.dateutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CaculationDateUtil {

    public static void main(String[] args) {
        //获取当前时间
        Date now = new Date();

        Date start = CaculationDateUtil.todayStart(now);
        Date end = CaculationDateUtil.addDayEnd(7);
        Date monthFirstDay = CaculationDateUtil.monthStart(now);
        Date monthLastDay = CaculationDateUtil.monthEnd(now);
        Date weekStart = CaculationDateUtil.weekStart(now);
        int getMonth = CaculationDateUtil.getMonth(now);
        boolean getIfHour = CaculationDateUtil.getIfHour(now , 1 , 10);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(start));
        System.out.println(format.format(end));
        System.out.println(format.format(monthFirstDay));
        System.out.println(format.format(monthLastDay));
        System.out.println(format.format(weekStart));
        System.out.println(getMonth);
        System.out.println(getIfHour);

    }

    /**
     * 获取今天开始时间 00：00：00
     * @return
     */
    public static Date todayStart(Date date){
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set( Calendar.HOUR_OF_DAY,0);
        start.set( Calendar.MINUTE, 0);
        start.set( Calendar.SECOND,0);
        start.set( Calendar.MILLISECOND,0);
        return start.getTime();
    }

    /**
     * 获取今天结束时间 23：59：59
     * @return
     */
    public static Date todayEnd(Date date){
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set( Calendar.HOUR_OF_DAY,23);
        start.set( Calendar.MINUTE, 59);
        start.set( Calendar.SECOND,59);
        start.set( Calendar.MILLISECOND,999);
        return start.getTime();
    }

    /**
     * 获取今天增加天数后的开始时间 00:00:00
     * @return
     */
    public static Date addDayStart(int day){
        Date now = todayStart(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, + day);
        calendar.set( Calendar.HOUR_OF_DAY,0);
        calendar.set( Calendar.MINUTE, 0);
        calendar.set( Calendar.SECOND,0);
        calendar.set( Calendar.MILLISECOND,0);
        return calendar.getTime();
    }


    /**
     * 获取指定某天增加天数后的开始时间 00:00:00
     * @return
     */
    public static Date addDayStart(Date date , int day){
        Date now = todayStart(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, + day);
        calendar.set( Calendar.HOUR_OF_DAY,0);
        calendar.set( Calendar.MINUTE, 0);
        calendar.set( Calendar.SECOND,0);
        calendar.set( Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 获取今天增加天数后的结束时间23：59：59
     * @return
     */
    public static Date addDayEnd(int day){
        Date now = todayStart(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, + day);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取本月一号的00：00：00
     * @return
     */
    public static Date monthStart(Date date){
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set( Calendar.HOUR_OF_DAY,0);
        start.set( Calendar.MINUTE, 0);
        start.set( Calendar.SECOND,0);
        start.set( Calendar.MILLISECOND,0);
        return start.getTime();
    }

    /**
     * 获取本月结束时间
     * @return
     */
    public static Date monthEnd(Date date){

        Date now = monthStart(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maxDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set( Calendar.MILLISECOND,999);

        return cal.getTime();
    }

    /**
     * 获取星期一的00：00：00
     * @return
     */
    public static Date weekStart(Date date){
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        if(start.get(Calendar.DAY_OF_WEEK)==1){
            start.add(Calendar.DAY_OF_MONTH, -1);
        }
        start.set(Calendar.DAY_OF_WEEK, 2);
        start.set( Calendar.HOUR_OF_DAY,0);
        start.set( Calendar.MINUTE, 0);
        start.set( Calendar.SECOND,0);
        start.set( Calendar.MILLISECOND,0);
        return start.getTime();
    }

    /**
     * 获取时间月份
     * @return
     */
    public static int getMonth(Date date){
        Calendar start = Calendar.getInstance();
        start.setTime(date);

        return start.get(Calendar.MONTH) + 1;
    }

    public static int getMonthDays(Date date){
        Calendar instance = Calendar.getInstance(Locale.CHINA);
        instance.setTime(date);
        return instance.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间小时是否在范围内
     * @return
     */
    public static boolean getIfHour(Date date, int startHour, int endHour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        boolean bool=false;
        if(hour>=startHour && hour<endHour){
            bool=true;
        }
        return bool;
    }

}
