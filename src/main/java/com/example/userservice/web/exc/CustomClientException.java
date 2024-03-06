package com.example.userservice.web.exc;

public class CustomClientException extends RuntimeException {
    public CustomClientException(String message) {
        super(message);
    }
    // Add any additional constructors or methods as needed
}
