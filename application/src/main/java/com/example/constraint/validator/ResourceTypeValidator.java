package com.example.constraint.validator;

import com.example.constraint.ResourceTypeConstraint;
import com.example.model.enumeration.ResourceType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ResourceTypeValidator implements ConstraintValidator<ResourceTypeConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            ResourceType resourceType = ResourceType.valueOf(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
