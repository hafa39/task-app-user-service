package com.example.userservice.web.exc;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException(String message) {
        super(message);
    }
    // Add any additional constructors or methods as needed
}
