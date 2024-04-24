package com.example.userservice.domain.user;

import com.example.userservice.domain.avatar.Avatar;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record User(
	String id,
	String username,
	String firstName,
	String lastName,
	List<String> roles
){}
