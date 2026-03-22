package com.doctorclinicapp.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object value) {
        super(resourceName + " not found with " + fieldName + " : " + value);
    }
}