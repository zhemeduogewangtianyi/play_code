package com.opencode.carrot.csearch.entity;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    @CField(enums = CFieldTypeEnum.LONG_POINT)
    private Long id ;

    @CField(enums = CFieldTypeEnum.TEXT_FIELD)
    private String name;

    @CField(enums = CFieldTypeEnum.TEXT_FIELD,analyzer = true)
    private String desc;

    @CField(enums = CFieldTypeEnum.INT_POINT)
    private Integer age;

    @CField(enums = CFieldTypeEnum.STRING_FIELD)
    private String url;

    @CField(enums = CFieldTypeEnum.LONG_POINT,isDate = true)
    private Date birthDay;

}
