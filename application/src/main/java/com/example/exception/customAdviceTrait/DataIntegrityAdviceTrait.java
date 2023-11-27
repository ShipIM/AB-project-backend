package com.example.exception.customAdviceTrait;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

public interface DataIntegrityAdviceTrait extends AdviceTrait {
    @ExceptionHandler
    default ResponseEntity<Problem> handleJDBC(final DataIntegrityViolationException e, final NativeWebRequest request) {
        return this.create(Status.CONFLICT, e, request);
    }
}
