package com.opencode.code.controller;

import com.opencode.code.signin.cache.SignInCacheStructure;
import com.opencode.code.signin.manager.SignInManager;
import com.opencode.code.signin.manager.TestSignInManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class SignInController {

    public static void main(String[] args) {
        //0.125
        double d = 0x1.0p-3;
        System.out.println(d);
    }

    @Autowired
    private SignInManager signInManager;

    @Autowired
    private TestSignInManager testSignInManager;

    @RequestMapping(value = "/signIn")
    public Object signIn(@RequestParam String type, @RequestParam String name) throws ParseException {
        return signInManager.signIn(type,name);
    }

    @RequestMapping(value = "/testSignIn")
    public Object testSignIn(@RequestParam String type, @RequestParam String name) throws ParseException {
        for(int y = 0 ; y < 100 ; y++){
            String dateStr = "202" + y;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = sdf.parse(dateStr);
            Calendar yearC = Calendar.getInstance();
            yearC.setTime(date);
            yearC.add(Calendar.YEAR,y);
            Date year = yearC.getTime();
            for(int m = 0 ; m < 12 ; m++){
//                Calendar monthC = Calendar.getInstance();
//                monthC.setTime(year);
//                monthC.add(Calendar.MONTH,m);
//                Date time = monthC.getTime();


                Calendar monthC = Calendar.getInstance();
                monthC.setTime(year);
                monthC.set(Calendar.YEAR, Integer.parseInt(dateStr));
                monthC.set(Calendar.MONTH,m);
                Date time = monthC.getTime();
                int days = monthC.getActualMaximum(Calendar.DAY_OF_MONTH);
                for(int d = 0 ; d < days ; d++){
                    Calendar dayAdd = Calendar.getInstance();
                    dayAdd.setTime(time);
                    dayAdd.add(Calendar.DATE,d);
                    Date dayAddTime = dayAdd.getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String ymd = simpleDateFormat.format(dayAddTime);
//                    System.out.println(ymd);
                    testSignInManager.signInBatch(type,name,dayAddTime);
                }

            }

        }


        return true;
    }

    @RequestMapping(value = "/getTypeMapInfo")
    public Object getTypeMapInfo() {
        return SignInCacheStructure.getTypeMapInfo();
    }

    @RequestMapping(value = "/getSignInCount")
    public Object getTypeMapInfo(@RequestParam String type, @RequestParam String name) throws ParseException {

        return signInManager.getSignInCount(type,name);
    }

    @RequestMapping(value = "/getSignInInfo")
    public Object getSignInInfo(@RequestParam String type, @RequestParam String name) throws ParseException {

        return signInManager.getSignInCount(type,name);
    }

}
