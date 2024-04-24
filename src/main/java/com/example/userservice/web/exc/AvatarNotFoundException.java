package com.example.userservice.web.exc;

public class AvatarNotFoundException extends NotFoundException{
    public AvatarNotFoundException(String userId) {
        super("Avatar for user with id: "+userId+" is not found");
    }
}
