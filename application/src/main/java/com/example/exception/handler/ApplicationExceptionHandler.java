package com.example.exception.handler;

import com.example.exception.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    /**
     * Обработка ошибок связанных с запросами несуществующих сущностей
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected final ResponseEntity<Object> EntityExceptionHandler(Exception ex) throws Exception {
        if (ex instanceof EntityNotFoundException exception) {
            return getDefaultErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        }

        throw ex;
    }

    /**
     * Обработка ошибок во время sql запросов
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected final ResponseEntity<Object> SqlExceptionHandler(Exception ex) throws Exception {
        if (ex instanceof DataIntegrityViolationException) {
            return getDefaultErrorResponse(ex.getCause().getLocalizedMessage(), HttpStatus.CONFLICT);
        }

        throw ex;
    }

    /**
     * Обработка ошибок связанных с валидностью переданных данных
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    protected final ResponseEntity<Map<String, List<String>>> handleConstraintViolationException
    (ConstraintViolationException e) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors",
                e.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isBlank())
                        .toList());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected final ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException exception) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors",
                exception.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isBlank())
                        .toList());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    protected final ResponseEntity<Object> getDefaultErrorResponse(String message, HttpStatusCode statusCode) {
        var body = new HashMap<String, Object>();
        body.put("errors", statusCode.toString() + " - " + message);
        return new ResponseEntity<>(body, statusCode);
    }
}
