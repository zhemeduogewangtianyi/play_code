package com.opencode.code.listeners.change2.enums;

public enum  TaskTypeEnum {

    REPORT(0,""),
    QUERY(1,""),
    GK(2,""),
    BK(3,""),
    DATA_RE_FLOW(4,""),
    PASSIVE(5,""),

    ;

    private Integer type;
    private String name;

    TaskTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static TaskTypeEnum getByType(Integer type){
        for(TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()){
            if(taskTypeEnum.getType().compareTo(type) == 0){
                return taskTypeEnum;
            }
        }
        return null;
    }

}
