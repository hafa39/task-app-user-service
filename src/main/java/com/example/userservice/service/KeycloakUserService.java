package com.example.userservice.service;


import com.example.userservice.domain.user.UserPayload;
import com.example.userservice.domain.user.User;
import org.keycloak.admin.client.resource.UserResource;

import java.util.List;

public interface KeycloakUserService {

    User getUserById(String userId);

    User getUserByUsername(String username);

    User updateUser(String userId, UserPayload userPayload);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);

    void updatePassword(String userId);

    public List<String> getRolesByUser(String userId);

}
