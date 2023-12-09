package com.example.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class IllegalAccessException extends AbstractThrowableProblem {
    public IllegalAccessException(String message) {
        super(
                null,
                "Illegal access",
                Status.FORBIDDEN,
                message);
    }
}
