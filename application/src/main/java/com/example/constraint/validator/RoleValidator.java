package com.example.constraint.validator;

import com.example.constraint.RoleConstraint;
import com.example.model.enumeration.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<RoleConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Role role = Role.valueOf(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
