package com.example.userservice.web.exc;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String userId) {
        super("User with id: "+userId+" is not found");
    }
}