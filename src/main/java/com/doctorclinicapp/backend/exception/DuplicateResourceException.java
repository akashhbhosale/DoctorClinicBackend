package com.doctorclinicapp.backend.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, Object value) {
        super(resourceName + " already exists with " + fieldName + " : " + value);
    }
}