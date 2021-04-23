package com.opencode.code.scan.service.impl;

import com.opencode.code.scan.annotation.ScanProcessor;
import com.opencode.code.scan.entity.RequestParam;

@ScanProcessor
public class UpdateFileProcessImpl extends BaseProcessor {

    @Override
    public Object process(RequestParam param) {
        return null;
    }

    @Override
    public void hook(RequestParam param) {

    }

    @Override
    public boolean available(RequestParam param) {
        return true;
    }

    @Override
    public boolean support(RequestParam param) {
        return true;
    }
}
