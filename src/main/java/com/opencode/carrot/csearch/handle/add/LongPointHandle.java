package com.opencode.carrot.csearch.handle.add;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import com.opencode.carrot.csearch.interfaces.CFieldHandle;
import org.apache.lucene.index.IndexableField;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author wty
 */
public class LongPointHandle implements CFieldHandle<CFieldContext,IndexableField> {

    @Override
    public IndexableField handle(CFieldContext context) {

        Object o = context.getO();

        if(o != null){

            Field field = context.getField();
            String name = field.getName();
            CField cField = context.getCField();
            CFieldTypeEnum enums = cField.enums();

            if(cField.isDate()){
                o = ((Date)o).getTime();
            }

            Class<?>[] parameter = {String.class,long[].class};
            Object[] args = {name,new long[]{Long.parseLong(o.toString())}};
            return enums.getClsField(parameter,args);
        }
        return null;
    }

    @Override
    public boolean support(CFieldContext context) {
        CField cField = context.getCField();
        return cField.enums().equals(CFieldTypeEnum.LONG_POINT);
    }

}
