package com.opencode.carrot.csearch.interfaces;

public interface Ordered {

    Integer MAX_VALUE = Integer.MAX_VALUE;
    Integer MIN_VALUE = Integer.MIN_VALUE;

    default int order(){
        return 0;
    }

}
