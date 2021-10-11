package com.opencode.code.valid;


import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class ValidationManager {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

    public static <T> void validate(T t, Class<? extends Group> group) throws RuntimeException {
        if(t == null){
            throw new RuntimeException("参数不能为空");
        }
        Validator validator = FACTORY.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, group);
        List<String> messageList = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(toList());
        if (!CollectionUtils.isEmpty(messageList)) {
            for (String message : messageList) {
                throw new RuntimeException(message);
            }
        }
    }
}
