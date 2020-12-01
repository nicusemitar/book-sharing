package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.UserRegistrationResponseDto;
import com.endava.booksharing.model.User;
import com.endava.booksharing.model.enums.RoleType;
import com.endava.booksharing.repository.RoleRepository;
import com.endava.booksharing.repository.UserRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.UserCredentialsExceptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.endava.booksharing.TestConstants.EMAIL_PASS;
import static com.endava.booksharing.TestConstants.USERNAME_PASS;
import static com.endava.booksharing.utils.RoleTestUtils.ROLE_USER;
import static com.endava.booksharing.utils.UserTestUtils.ExpectedDummyUser;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_REQUEST_DTO_PASS;
import static com.endava.booksharing.utils.UserTestUtils.USER_RESPONSE_DTO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userRepository, roleRepository);
    }

    @Test
    void shouldSetDefaultRoleToUser() {
        final User EXPECTED_USER = ExpectedDummyUser();

        when(roleRepository.getRoleByRoleType(RoleType.USER)).thenReturn(Optional.of(ROLE_USER()));

        final User ACTUAL_USER = User.builder().build();
        userService.setDefaultRoleToUser(ACTUAL_USER);

        assertEquals(EXPECTED_USER.getUserRoles().size(), ACTUAL_USER.getUserRoles().size());
        assertEquals(EXPECTED_USER.getUserRoles().contains(ROLE_USER()), ACTUAL_USER.getUserRoles().contains(ROLE_USER()));

        verify(roleRepository).getRoleByRoleType(RoleType.USER);
    }

    @Test
    void shouldThrowRoleNotFoundOnDefaultRoleSet() {
        when(roleRepository.getRoleByRoleType(RoleType.USER)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.setDefaultRoleToUser(User.builder().build()));
    }


    @Test
    void shouldSaveUser() {
        final UserRegistrationResponseDto EXPECTED_RESPONSE_USER = USER_RESPONSE_DTO;

        when(userRepository.existsUserByUsernameOrEmail(USERNAME_PASS, EMAIL_PASS)).thenReturn(false);
        when(roleRepository.getRoleByRoleType(RoleType.USER)).thenReturn(Optional.of(ROLE_USER()));
        when(userRepository.save(any(User.class))).thenReturn(USER_ONE());

        final UserRegistrationResponseDto ACTUAL_RESPONSE_USER = userService.saveUser(USER_REQUEST_DTO_PASS);

        assertAll(
                () -> assertEquals(EXPECTED_RESPONSE_USER.getUsername(), ACTUAL_RESPONSE_USER.getUsername()),
                () -> assertEquals(EXPECTED_RESPONSE_USER.getEmail(), ACTUAL_RESPONSE_USER.getEmail())
        );

        verify(userRepository).existsUserByUsernameOrEmail(USERNAME_PASS, EMAIL_PASS);
        verify(userRepository).save(any(User.class));
        verify(roleRepository).getRoleByRoleType(RoleType.USER);
    }

    @Test
    void shouldFindExistentUserInDatabase() {
        when(userRepository.existsUserByUsernameOrEmail(USER_REQUEST_DTO_PASS.getUsername(), USER_REQUEST_DTO_PASS.getEmail())).thenReturn(true);

        assertThrows(UserCredentialsExceptions.class, () -> userService.saveUser(USER_REQUEST_DTO_PASS));

        verify(userRepository).existsUserByUsernameOrEmail(USER_REQUEST_DTO_PASS.getUsername(), USER_REQUEST_DTO_PASS.getEmail());
    }

    @Test
    void shouldCatchException() {
        when(userRepository.existsUserByUsernameOrEmail(USER_REQUEST_DTO_PASS.getUsername(), USER_REQUEST_DTO_PASS.getEmail())).thenThrow(new NotFoundException("Unable to get role from database"));

        assertThrows(UserCredentialsExceptions.class, () -> userService.saveUser(USER_REQUEST_DTO_PASS));

        verify(userRepository).existsUserByUsernameOrEmail(USER_REQUEST_DTO_PASS.getUsername(), USER_REQUEST_DTO_PASS.getEmail());
    }
}
