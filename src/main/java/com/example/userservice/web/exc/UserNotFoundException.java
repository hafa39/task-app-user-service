package com.example.userservice.web.exc;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    // Add any additional constructors or methods as needed
}