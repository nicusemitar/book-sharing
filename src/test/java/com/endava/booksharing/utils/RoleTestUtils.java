package com.endava.booksharing.utils;


import com.endava.booksharing.model.Role;
import com.endava.booksharing.model.enums.RoleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleTestUtils {
    public static final Set<Role> AUTHORITES = new HashSet<>(Arrays.asList(
            Role.builder().roleType(RoleType.ADMIN).build()
            , Role.builder().roleType(RoleType.USER).build()));

    public static Set<Role> USER_ROLES() {
        Set<Role> roles = new HashSet<>();
        roles.add(ROLE_USER());
        return roles;
    }

    public static Role ROLE_USER() {
        Role role = new Role();
        role.setRoleType(RoleType.USER);
        return role;
    }
}
