package com.opencode.code.signin.bean;

import lombok.Data;

import java.util.Date;

@Data
public class SignInContext {

    /** 签到的所属类型 */
    private String type;

    /** 签到人名字 */
    private String username;

    /** 签到所属年份 yyyy-MM-dd */
    private String year;

    /** 签到所属月份 */
    private String month;

    /** 本月累计签到天数 int 数字 */
    private Integer bitSet;

    /** 累计天数 */
    private Integer countDay;

    /** 首次打卡时间 */
    private Date firstSignInTime;

    /** 最后打卡时间 */
    private Date lastSignInTime;



}
