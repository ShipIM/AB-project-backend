package com.example.exceptions.customExceptions;

public class ArgumentNotValid extends Exception {
    public ArgumentNotValid(String message) {
        super(message);
    }

    public ArgumentNotValid(String message, Throwable throwable) {
        super(message, throwable);
    }
}
