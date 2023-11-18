package com.example.exceptions;

import com.example.exceptions.customExceptions.ArgumentNotValid;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler  extends ResponseEntityExceptionHandler {
    /**
     * Обработка ошибок связанных с валидностью переданных данных обработанных без @Valid
     */
    @ExceptionHandler({ArgumentNotValid.class})
    protected final ResponseEntity<Object> ArgumentNotValidHandler(Exception ex) throws Exception {
        if (ex instanceof ArgumentNotValid exception) {
            return getDefaultErrorResponse(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        throw ex;
    }

    /**
     * Обработка ошибок связанных с запросами несуществующих сущностей
     */
    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class})
    protected final ResponseEntity<Object> EntityExceptionHandler(Exception ex) throws Exception {
        if (ex instanceof EntityNotFoundException exception) {
            return getDefaultErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        } else if (ex instanceof EntityExistsException exception) {
            return getDefaultErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
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
     * Обработка ошибок связанных с валидностью переданных данных обработанных при помощи @Valid
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        {
            Map<String, Object> body = new HashMap<>();
            body.put("errors",
                    ex.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .map(message -> status + " - " + message)
                            .filter(s -> !s.isBlank())
                            .toList());

            return new ResponseEntity<>(body, status);
        }
    }

    protected ResponseEntity<Object> getDefaultErrorResponse(String message, HttpStatusCode statusCode) {
        var body = new HashMap<String, Object>();
        body.put("errors", statusCode.toString() + " - " + message);
        return new ResponseEntity<>(body, statusCode);
    }
}
