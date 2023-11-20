package com.example.constraint.validator;

import com.example.constraint.ContentTypeConstraint;
import com.example.model.enumeration.ContentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContentTypeValidator implements ConstraintValidator<ContentTypeConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            ContentType contentType = ContentType.valueOf(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
