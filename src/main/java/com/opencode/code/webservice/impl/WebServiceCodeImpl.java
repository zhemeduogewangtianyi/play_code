package com.opencode.code.webservice.impl;

import com.opencode.code.webservice.WebServiceCode;

import javax.jws.WebService;

@WebService(endpointInterface = "com.opencode.code.webservice.WebServiceCode", serviceName = "WebServiceCodeImpl", targetNamespace = "gt_code")
public class WebServiceCodeImpl implements WebServiceCode {
    @Override
    public Object wei(String args) {
        return args + " _ OK !";
    }
}
