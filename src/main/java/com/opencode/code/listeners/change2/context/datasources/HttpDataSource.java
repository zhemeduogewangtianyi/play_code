package com.opencode.code.listeners.change2.context.datasources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpDataSource {

    private String url;

    private Integer header ;

    private String requestMethod ;

    private String body;

}
