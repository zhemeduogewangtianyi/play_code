package com.opencode.code.webservice.endpoint;


import com.opencode.code.webservice.impl.WebServiceCodeImpl;

import javax.xml.ws.Endpoint;

public class PublisherEndPoint {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8088/wei",new WebServiceCodeImpl());
    }

}
