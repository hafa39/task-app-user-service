package com.example.userservice.web;

import com.example.userservice.domain.User;
import com.example.userservice.web.exc.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class UserController {

    @Value("${keycloak.host}")
    private String keycloakHost;
    private WebClient webClient;

    public UserController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/users/{id}")
    public Object getUserById(@RegisteredOAuth2AuthorizedClient("keycloak")
                              OAuth2AuthorizedClient authorizedClient,
                              @PathVariable(name = "id") String userId) {
        return webClient
                .get()
                .uri(keycloakHost+"/admin/realms/TaskAgile/users/"+userId)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(User.class)
                .onErrorResume(Exception.class, GlobalExceptionHandler::handleError)
                .block();
    }
}
