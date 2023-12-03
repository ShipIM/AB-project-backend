package com.example.constraint.validator;

import com.example.constraint.UserStatusConstraint;
import com.example.model.enumeration.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserStatusValidator implements ConstraintValidator<UserStatusConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Status status = Status.valueOf(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}