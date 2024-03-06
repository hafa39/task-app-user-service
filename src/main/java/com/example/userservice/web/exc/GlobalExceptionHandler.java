package com.example.userservice.web.exc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class) // Catches any other exceptions
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    public static <T> Mono<T> handleError(Throwable throwable) {
        logger.info("Exception: "+throwable.getMessage());
        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException exception = (WebClientResponseException) throwable;
            if (exception.getStatusCode().is4xxClientError()) {
                if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    return Mono.error(new UserNotFoundException("User not found"));
                } else {
                    return Mono.error(new CustomClientException("Client error occurred"));
                }
            }
        }
        return Mono.error(new CustomClientException("Server error occurred"));
    }
}

