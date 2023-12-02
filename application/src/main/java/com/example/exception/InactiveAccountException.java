package com.example.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InactiveAccountException extends AbstractThrowableProblem {
    public InactiveAccountException(String message) {
        super(
                null,
                "Inactive user account",
                Status.FORBIDDEN,
                message);
    }
}
