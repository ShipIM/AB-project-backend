package com.example.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class QuartzNoEntityException extends AbstractThrowableProblem {
    public QuartzNoEntityException(String message) {
        super(
                null,
                "No such quartz entity",
                Status.BAD_REQUEST,
                message);
    }
}