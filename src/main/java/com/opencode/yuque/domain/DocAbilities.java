package com.opencode.yuque.domain;

import lombok.Data;

@Data
public class DocAbilities {

    private Boolean read;
    private Boolean update;
    private Boolean destroy;
    private Boolean create;

}
