package com.endava.booksharing.repository;

import com.endava.booksharing.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"userRoles"})
    Optional<User> getUserByUsername(String username);

    boolean existsUserByUsernameOrEmail(String username, String email);
}
