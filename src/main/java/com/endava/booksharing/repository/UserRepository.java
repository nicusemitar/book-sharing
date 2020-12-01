package com.endava.booksharing.repository;

import com.endava.booksharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsernameOrEmail(String username, String email);
}
