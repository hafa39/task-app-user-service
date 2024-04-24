package com.example.userservice.domain.avatar;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AvatarRepository extends CrudRepository<Avatar,Long> {

    Optional<Avatar> findByUserId(String userId);
}
