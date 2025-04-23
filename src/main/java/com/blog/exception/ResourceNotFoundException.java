package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String fieldValue, String fieldName, String resourceName) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}