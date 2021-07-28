package com.opencode.carrot.csearch.context;

import com.opencode.carrot.csearch.annotation.CField;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.document.Document;

import java.lang.reflect.Field;

@Getter
public class CFieldContext {

    @Setter
    private String name;

    @Setter
    private Class<?> type;

    @Setter
    private CField cField;

    private Field field;

    @Setter
    private Object o;

    private final Document doc;

    public CFieldContext(Document doc) {
        this.doc = doc;
    }

    public void setField(Field field){
        field.setAccessible(true);
        this.field = field;
    }
}
