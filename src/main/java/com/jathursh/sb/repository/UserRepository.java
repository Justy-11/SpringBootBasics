package com.jathursh.sb.repository;

import com.jathursh.sb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);  // returns an Optional object that either contains the user entity or is empty if the entity is not found.
                                                           // The Optional type is used here to avoid null pointer exceptions when the entity is not found.
    Boolean existByUsername(String username);  // checks if a user with the specified username exists in the database
}
