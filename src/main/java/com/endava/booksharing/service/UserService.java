package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.UserRegistrationRequestDto;
import com.endava.booksharing.api.dto.UserRegistrationResponseDto;
import com.endava.booksharing.model.Role;
import com.endava.booksharing.model.User;
import com.endava.booksharing.model.enums.RoleType;
import com.endava.booksharing.repository.RoleRepository;
import com.endava.booksharing.repository.UserRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.UserCredentialsExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.endava.booksharing.utils.mappers.UserMapper.mapUserRequestDtoToUser;
import static com.endava.booksharing.utils.mappers.UserMapper.mapUserToUserResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Qualifier("bCryptPasswordEncoder")
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserRegistrationResponseDto saveUser(UserRegistrationRequestDto userRegistrationRequestDto) {
        log.info("Saving user with username [{}]", userRegistrationRequestDto.getUsername());
        try {
            if (userRepository.existsUserByUsernameOrEmail(userRegistrationRequestDto.getUsername(), userRegistrationRequestDto.getEmail())) {
                log.warn("User with that credentials already exists in database");
                throw new UserCredentialsExceptions("User with that credentials already exists in database");
            }
            User user = mapUserRequestDtoToUser.apply(userRegistrationRequestDto);
            setDefaultRoleToUser(user);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            return mapUserToUserResponseDto.apply(user);
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            throw new UserCredentialsExceptions("Unable to save user to database due to internal issues");
        }
    }

    public void setDefaultRoleToUser(User user) {
        Role role = roleRepository.getRoleByRoleType(RoleType.USER).orElseThrow(() -> {
            log.warn("Role wasn't extracted from database");
            return new NotFoundException("Role was not found in database");
        });
        log.info("Setting role: [{}] for [{}]", role.getRoleType(), user.getUsername());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setUserRoles(roleSet);
    }
}
