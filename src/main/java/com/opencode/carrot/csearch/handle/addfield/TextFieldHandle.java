package com.opencode.carrot.csearch.handle.addfield;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import com.opencode.carrot.csearch.interfaces.CFieldHandle;
import org.apache.lucene.index.IndexableField;

/**
 * @author wty
 */
public class TextFieldHandle implements CFieldHandle<CFieldContext, IndexableField> {

    @Override
    public IndexableField handle(CFieldContext context) {
        return null;
    }

    @Override
    public boolean support(CFieldContext context) {
        CField cField = context.getCField();
        return cField.enums().equals(CFieldTypeEnum.TEXT_FIELD);
    }

}
