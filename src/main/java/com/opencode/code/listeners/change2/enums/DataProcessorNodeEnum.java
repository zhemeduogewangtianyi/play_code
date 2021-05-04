package com.opencode.code.listeners.change2.enums;

public enum DataProcessorNodeEnum {

    PRODUCER(0,"PRODUCER:","PRODUCER:"),
    CONSUMER(1,"PRODUCER:","CONSUMER:"),
    PUSH(2,"CONSUMER:","PUSH:"),
    END(3,"PUSH:","END:")

    ;

    private Integer type;
    private String receiver;
    private String sender;

    DataProcessorNodeEnum(Integer type, String receiver,String sender) {
        this.type = type;
        this.receiver = receiver;
        this.sender = sender;
    }

    public Integer getType() {
        return type;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public static DataProcessorNodeEnum getByType(Integer type){
        for(DataProcessorNodeEnum taskTypeEnum : DataProcessorNodeEnum.values()){
            if(taskTypeEnum.getType().compareTo(type) == 0){
                return taskTypeEnum;
            }
        }
        return null;
    }

}
