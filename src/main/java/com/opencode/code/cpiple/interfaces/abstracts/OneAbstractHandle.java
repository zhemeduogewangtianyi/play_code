package com.opencode.code.cpiple.interfaces.abstracts;

import com.opencode.code.cpiple.context.OneContext;
import com.opencode.code.cpiple.interfaces.HandleInterface;

public abstract class OneAbstractHandle implements HandleInterface<OneContext> {

    public Object handle(OneContext oneContext){
        if(oneContext == null){
            return "oneContext is null !";
        }
        return null;
    }

}
