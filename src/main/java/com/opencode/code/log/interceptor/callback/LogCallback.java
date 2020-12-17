package com.opencode.code.log.interceptor.callback;


import com.opencode.code.log.interceptor.result.LogResult;

public interface LogCallback {

    boolean call(LogResult logResult);

}
