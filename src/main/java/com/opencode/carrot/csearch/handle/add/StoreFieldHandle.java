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
public class StoreFieldHandle implements CFieldHandle<CFieldContext, IndexableField> {

    @Override
    public IndexableField handle(CFieldContext context) {
        Object o = context.getO();

        if( o != null){

            Field field = context.getField();
            String name = field.getName();
            CField cField = context.getCField();
            CFieldTypeEnum enums = CFieldTypeEnum.STORED_FIELD;

            if(cField.isDate()){
                o = ((Date)o).getTime();
            }

            Class<?>[] parameter = {String.class,String.class};
            Object[] args = {name,o.toString()};

            return enums.getClsField(parameter,args);
        }
        return null;
    }

    @Override
    public boolean support(CFieldContext context) {
        CField cField = context.getCField();
        if(cField.enums().equals(CFieldTypeEnum.TEXT_FIELD)){
            return false;
        }
        if(cField.enums().equals(CFieldTypeEnum.STRING_FIELD)){
            return false;
        }
        return cField.store();
    }

}
