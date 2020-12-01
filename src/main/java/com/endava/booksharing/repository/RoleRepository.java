package com.endava.booksharing.repository;

import com.endava.booksharing.model.Role;
import com.endava.booksharing.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByRoleType(RoleType roleType);
}
