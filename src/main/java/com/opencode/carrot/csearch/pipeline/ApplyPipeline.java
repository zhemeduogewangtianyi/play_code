package com.opencode.carrot.csearch.pipeline;

import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.interfaces.CFieldHandle;
import com.opencode.carrot.csearch.interfaces.CFieldInterceptor;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ApplyPipeline<T> {

    private final List<CFieldInterceptor<T>> INTERCEPTORS = new ArrayList<>();

    private static final String[] names = {};

    private int index = -1;

    {
        ExtensionLoader<CFieldInterceptor> extensionLoader = ExtensionLoader.getExtensionLoader(CFieldInterceptor.class);
        for(String name : names){
            INTERCEPTORS.add(extensionLoader.getExtension(name));
        }
        INTERCEPTORS.sort(new Comparator<CFieldInterceptor>() {
            @Override
            public int compare(CFieldInterceptor o1, CFieldInterceptor o2) {
                return o1.order() - o2.order();
            }
        });
    }

    public Object applyPre(CFieldContext context) throws Throwable {
        for(index = 0 ; index < INTERCEPTORS.size() ; index++){
            CFieldInterceptor cFieldInterceptor = INTERCEPTORS.get(index);
            if(!cFieldInterceptor.available(context)){
                continue;
            }
            if(!cFieldInterceptor.support(context)){
                continue;
            }
            return cFieldInterceptor.pre(context);
        }
        return null;
    }

    //todo 写了一半
    public void applyPost(CFieldContext context){

    }

}
