package com.opencode.carrot.csearch.enums;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexableField;

public enum CFieldTypeEnum {
    /***/
    INT_POINT("存储 int 类型" , IntPoint.class),
    LONG_POINT("存储 long 类型" ,LongPoint.class),
    TEXT_FIELD("存储 string 类型，分词" ,TextField.class),
    STRING_FIELD("存储 string 类型，不分词" ,StringField.class),
    ;

    private String desc;
    private final Class<?> cls;

    CFieldTypeEnum(String desc , Class<?> cls) {
        this.cls = cls;
    }

    public IndexableField getClsField(Class<?>[] parameter, Object[] args) {
        try {
            Object o = cls.getDeclaredConstructor(parameter).newInstance(args);
            return (IndexableField)o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }
}
