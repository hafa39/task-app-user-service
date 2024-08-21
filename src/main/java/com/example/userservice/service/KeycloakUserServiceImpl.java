package com.example.userservice.service;

import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UserPayload;
import com.example.userservice.web.exc.UserNotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

    public KeycloakUserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public User getUserById(String userId) {
        UsersResource usersResource = getUsersResource();
        UserRepresentation ur = usersResource.get(userId).toRepresentation();
        List<String> roles = getRolesByUser(userId);
        return new User(ur.getId(),ur.getUsername(),ur.getFirstName(),ur.getLastName(),roles);
    }

    @Override
    public User getUserByUsername(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> search = usersResource.search(username);
        if (search.size()==0) throw new UserNotFoundException(username);
        UserRepresentation ur = search.get(0);
        List<String> roles = getRolesByUser(ur.getId());
        return new User(ur.getId(),ur.getUsername(),ur.getFirstName(),ur.getLastName(),roles);
    }

    @Override
    public User updateUser(String userId, UserPayload userPayload) {
        System.out.println("User payload: " + userPayload);
        UserRepresentation ur = new UserRepresentation();
        ur.setFirstName(userPayload.firstName());
        ur.setLastName(userPayload.lastName());
        System.out.println("User resp: " + ur);
        getUsersResource().get(userId).update(ur);

        User updated = getUserById(userId);
        System.out.println("User updated successfully: " + updated);
        return new User(updated.id(),updated.username(), updated.firstName(), updated.lastName(), updated.roles());
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    public List<String> getRolesByUser(String userId){
        UserResource userResource = getUsersResource().get(userId);
        List<RoleRepresentation> roleRepresentations = userResource.roles().realmLevel().listAll();
        List<String> roles = roleRepresentations.stream().map(RoleRepresentation::getName).collect(Collectors.toList());
        return roles;
    }

    @Override
    public void deleteUserById(String userId) {

    }

    @Override
    public void emailVerification(String userId) {

    }

    @Override
    public UserResource getUserResource(String userId) {
        return null;
    }

    @Override
    public void updatePassword(String userId) {

    }
}
