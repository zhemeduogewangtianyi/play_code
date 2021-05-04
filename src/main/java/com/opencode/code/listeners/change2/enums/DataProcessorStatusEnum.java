package com.opencode.code.listeners.change2.enums;

public enum DataProcessorStatusEnum {

    FAIL(0,"失败"),
    PROCESSOR(1,"处理中"),
    SUCCESS(2,"成功"),

    ;

    private Integer type;
    private String name;

    DataProcessorStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static DataProcessorStatusEnum getByType(Integer type){
        for(DataProcessorStatusEnum taskTypeEnum : DataProcessorStatusEnum.values()){
            if(taskTypeEnum.getType().compareTo(type) == 0){
                return taskTypeEnum;
            }
        }
        return null;
    }

}
