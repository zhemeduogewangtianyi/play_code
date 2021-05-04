package com.opencode.code.listeners.change2.enums;

public enum DataSourceTypeEnum {

    ODPS(0,""),
    METAQ(1,""),
    HTTP(2,""),
    HTTPS(3,""),
    TT(4,""),

    ;

    private Integer type;
    private String name;

    DataSourceTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static DataSourceTypeEnum getByType(Integer type){
        for(DataSourceTypeEnum taskTypeEnum : DataSourceTypeEnum.values()){
            if(taskTypeEnum.getType().compareTo(type) == 0){
                return taskTypeEnum;
            }
        }
        return null;
    }

}
