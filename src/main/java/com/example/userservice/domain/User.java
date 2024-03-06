package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record User(
	String id,
	String username,
	String firstName,
	String lastName
){}
