package com.opencode.shuma.enums;

/**
 * 勋章枚举类
 * */
public enum MedalEnum {

    COURAGE(1,"勇气"),
    FRIENDSHIP(2,"友情"),
    HONEST(3,"诚实"),
    LOVE(4,"爱心"),
    KNOWLEDGE(5,"知识"),
    PURE(6,"纯真"),
    HOPE(7,"希望"),
    LIGHT(8,"光明"),
    TENDER(9,"温柔"),

    ;

    private Integer code;

    private String value;

    MedalEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public MedalEnum getByCode(Integer code){
        for(MedalEnum medalEnum : MedalEnum.values()){
            if(medalEnum.code.compareTo(code) == 0){
                return medalEnum;
            }
        }
        return null;
    }

}
