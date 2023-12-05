package com.example.constraint.validator;

import com.example.constraint.ResourceTypeConstraint;
import com.example.model.enumeration.ResourceType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ResourceTypeValidator implements ConstraintValidator<ResourceTypeConstraint, String> {

    private boolean canNull;

    @Override
    public void initialize(ResourceTypeConstraint constraintAnnotation) {
        canNull = constraintAnnotation.canNull();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (canNull && s == null) {
            return true;
        }

        try {
            ResourceType resourceType = ResourceType.valueOf(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
