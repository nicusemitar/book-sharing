package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.UserRegistrationRequestDto;
import com.endava.booksharing.api.dto.UserRegistrationResponseDto;
import com.endava.booksharing.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.endava.booksharing.TestConstants.*;
import static com.endava.booksharing.utils.RoleTestUtils.USER_ROLES;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestUtils {
    public static final UserRegistrationRequestDto USER_REQUEST_DTO_USERNAME_FAIL = UserRegistrationRequestDto.builder()
            .username(USERNAME_FAIL)
            .email(EMAIL_PASS)
            .password(PASSWORD_PASS)
            .build();
    public static final UserRegistrationRequestDto USER_REQUEST_DTO_EMPTY = UserRegistrationRequestDto.builder()
            .username("")
            .email("")
            .password("")
            .build();
    public static final UserRegistrationRequestDto USER_REQUEST_DTO_EMAIL_FAIL = UserRegistrationRequestDto.builder()
            .username(USERNAME_PASS)
            .email(EMAIL_FAIL)
            .password(PASSWORD_PASS)
            .build();

    public static final UserRegistrationRequestDto USER_REQUEST_DTO_PASS = UserRegistrationRequestDto.builder()
            .username(USERNAME_PASS)
            .email(EMAIL_PASS)
            .password(PASSWORD_PASS)
            .build();
    public static final UserRegistrationRequestDto USER_REQUEST_DTO_PASSWORD_FAIL = UserRegistrationRequestDto.builder()
            .username(USERNAME_PASS)
            .email(EMAIL_PASS)
            .password(PASSWORD_FAIL)
            .build();
    public static final UserRegistrationResponseDto USER_RESPONSE_DTO = UserRegistrationResponseDto.builder()
            .username(USERNAME_PASS)
            .email(EMAIL_PASS)
            .build();

    public static User USER_ONE() {
        return User.builder().username(USERNAME_PASS).email(EMAIL_PASS).build();
    }

    public static User ExpectedDummyUser() {
        return User.builder().userRoles(USER_ROLES()).build();
    }
}
