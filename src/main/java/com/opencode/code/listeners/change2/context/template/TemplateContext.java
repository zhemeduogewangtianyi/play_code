package com.opencode.code.listeners.change2.context.template;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateContext {

    private Long templateId;

    private Integer status;

    private String template;

    private String fileName;

    private Integer needZip;

    private String zipPassword;

}
