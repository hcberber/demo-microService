package com.microservice.auth.repository;

import com.microservice.auth.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<User> findByUsernameAndIsActiveTrue(String username);

    boolean existsByUsername(String username);
}
