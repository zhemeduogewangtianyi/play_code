package com.opencode.code.listeners.change2.enums;

public enum DeliveryTypeEnum {

    FTP(0,""),
    SFTP(1,""),
    OSS(2,""),
    ODPS(3,""),
    SLS(4,""),
    HTTP(4,""),
    HTTPS(4,""),

    ;

    private Integer type;
    private String name;

    DeliveryTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static DeliveryTypeEnum getByType(Integer type){
        for(DeliveryTypeEnum taskTypeEnum : DeliveryTypeEnum.values()){
            if(taskTypeEnum.getType().compareTo(type) == 0){
                return taskTypeEnum;
            }
        }
        return null;
    }

}
