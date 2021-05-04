package com.opencode.code.listeners.impl;

import lombok.Data;

@Data
public class ApprovalParam {

    private Long id;

    private String name;

    private Integer status;

    private String createPerson;

    private String approver;


}
