package com.example.userservice.web;

import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UserPayload;
import com.example.userservice.service.KeycloakUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);
    private final KeycloakUserService keycloakUserService;

    public UserController(KeycloakUserService keycloakUserService) {
        this.keycloakUserService = keycloakUserService;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(name = "id") String userId) {
        log.info("fetch user by id {}", userId);
        User userById = keycloakUserService.getUserById(userId);
        return userById;
    }

    @GetMapping("/users")
    public User getUserByUsername(@RequestParam(name = "username") String username) {
        log.info("fetch user by username {}", username);
        User userById = keycloakUserService.getUserByUsername(username);
        return userById;
    }

    @GetMapping("/extern/user")
    public User getUser(@AuthenticationPrincipal Jwt jwt) {
        log.info("access from {}", jwt.getSubject());
        return keycloakUserService.getUserById(jwt.getSubject());
    }

    @PutMapping("/extern/user")
    public User putUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserPayload userPayload) {
        log.info("put user {}", jwt.getSubject());
        return keycloakUserService.updateUser(jwt.getSubject(),userPayload);
    }
}
