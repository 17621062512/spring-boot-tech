package com.ray.tech.service.factory;

public class NoSuchDataSourceException extends Exception {
    public NoSuchDataSourceException(String message) {
        super(message);
    }

    public NoSuchDataSourceException() {
    }
}
