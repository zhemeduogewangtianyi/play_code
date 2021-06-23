package com.opencode.carrot.csearch.handle.addfield;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import com.opencode.carrot.csearch.interfaces.CFieldHandle;
import org.apache.lucene.index.IndexableField;

import java.lang.reflect.Field;

/**
 * @author wty
 */
public class LongPointHandle implements CFieldHandle<CFieldContext,IndexableField> {

    @Override
    public IndexableField handle(CFieldContext context) {

        Field field = context.getField();
        String name = field.getName();
        Object o = context.getO();
        CField cField = context.getCField();
        CFieldTypeEnum enums = cField.enums();

        Class<?>[] parameter = {String.class,long[].class};
        Object[] args = {name,new long[]{Long.parseLong(o.toString())}};
        IndexableField clsField = enums.getClsField(parameter,args);
        return clsField;
    }

    @Override
    public boolean support(CFieldContext context) {
        CField cField = context.getCField();
        return cField.enums().equals(CFieldTypeEnum.LONG_POINT);
    }

}
