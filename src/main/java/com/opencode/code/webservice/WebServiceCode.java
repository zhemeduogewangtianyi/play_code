package com.opencode.code.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "com.opencode.code.webservice.WebServiceCode", serviceName = "WebServiceCode", targetNamespace = "gt_code")
public interface WebServiceCode {

    Object wei(String args);

}
