package com.opencode.carrot.csearch.interfaces;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface CFieldHandle<T,R> extends Supported<T>{

    R handle(T context);

}
