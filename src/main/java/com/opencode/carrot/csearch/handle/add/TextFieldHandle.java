package com.opencode.carrot.csearch.handle.add;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import com.opencode.carrot.csearch.interfaces.CFieldHandle;
import org.apache.lucene.index.IndexableField;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wty
 */
public class TextFieldHandle implements CFieldHandle<CFieldContext, IndexableField> {

    @Override
    public IndexableField handle(CFieldContext context) {
        Object o = context.getO();

        if( o != null){

            Field field = context.getField();
            String name = field.getName();
            CField cField = context.getCField();
            CFieldTypeEnum enums = cField.enums();

            if(cField.isDate()){
                o = new SimpleDateFormat(cField.dateFormat()).format(((Date)o));
            }

            Class<?>[] parameter = {String.class,String.class, org.apache.lucene.document.Field.Store.class};
            Object[] args = {name,o.toString(),cField.store() ? org.apache.lucene.document.Field.Store.YES : org.apache.lucene.document.Field.Store.YES};

            return enums.getClsField(parameter,args);
        }
        return null;
    }

    @Override
    public boolean support(CFieldContext context) {
        CField cField = context.getCField();
        return cField.enums().equals(CFieldTypeEnum.TEXT_FIELD);
    }

}
