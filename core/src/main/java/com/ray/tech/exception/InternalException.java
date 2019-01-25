package com.ray.tech.exception;

public class InternalException extends RuntimeException {

    private String message;

    public InternalException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
