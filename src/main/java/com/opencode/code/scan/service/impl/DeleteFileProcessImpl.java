package com.opencode.code.scan.service.impl;

import com.alibaba.fastjson.JSON;
import com.opencode.code.scan.annotation.ScanProcessor;
import com.opencode.code.scan.entity.RequestParam;

@ScanProcessor
public class DeleteFileProcessImpl extends BaseProcessor {

    @Override
    public Object process(RequestParam param) {
        System.out.println("DeleteFileProcessImpl " + JSON.toJSONString(param));
        return null;
    }

    @Override
    public boolean available(RequestParam param) {
        return true;
    }

    @Override
    public boolean support(RequestParam param) {
        Long createTime = param.getTime();
        if(createTime > System.currentTimeMillis()){
            return true;
        }
        return false;
    }

    @Override
    public void hook(RequestParam param) {

    }

}
