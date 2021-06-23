package com.opencode.carrot.csearch.annotation;

import com.opencode.carrot.csearch.enums.CFieldTypeEnum;

import java.lang.annotation.*;

/**
 * @author wty
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CField {

    CFieldTypeEnum enums();

    boolean store() default true;

    boolean analyzer() default false;

    boolean isDate() default false;

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

}
