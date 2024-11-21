package com.youcode.citronix.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName) {
        super(entityName + " not found");
    }
}
