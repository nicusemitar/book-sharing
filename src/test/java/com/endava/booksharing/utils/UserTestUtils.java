package com.endava.booksharing.utils;


import com.endava.booksharing.api.dto.UserRegistrationRequestDto;
import com.endava.booksharing.api.dto.UserRegistrationResponseDto;
import com.endava.booksharing.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import static com.endava.booksharing.TestConstants.EMAIL_FAIL;
import static com.endava.booksharing.TestConstants.EMAIL_PASS;
import static com.endava.booksharing.TestConstants.ID_TWO;
import static com.endava.booksharing.TestConstants.PASSWORD_FAIL;
import static com.endava.booksharing.TestConstants.PASSWORD_PASS;
import static com.endava.booksharing.TestConstants.USERNAME_FAIL;
import static com.endava.booksharing.TestConstants.USERNAME_PASS;
import static com.endava.booksharing.TestConstants.USER_ID;
import static com.endava.booksharing.TestConstants.USER_ONE_PASSWORD;
import static com.endava.booksharing.TestConstants.USER_ONE_PASSWORD_ENCODED;
import static com.endava.booksharing.TestConstants.USER_ONE_USERNAME;
import static com.endava.booksharing.TestConstants.USER_TWO_PASSWORD;
import static com.endava.booksharing.TestConstants.USER_TWO_USERNAME;
import static com.endava.booksharing.utils.RoleTestUtils.AUTHORITES;
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
    public static final User USER_ONE =
            User.builder()
                    .id(USER_ID)
                    .username(USER_ONE_USERNAME)
                    .password(USER_ONE_PASSWORD)
                    .userRoles(AUTHORITES)
                    .build();
    public static final User USER_TWO =
            User.builder()
                    .id(ID_TWO)
                    .username(USER_TWO_USERNAME)
                    .password(USER_TWO_PASSWORD)
                    .userRoles(AUTHORITES)
                    .build();

    public static final UserDetails USER_DETAILS =
            new org.springframework.security.core.userdetails.User(USER_ONE.getUsername(), USER_ONE_PASSWORD_ENCODED, AUTHORITES);

    public static User USER_ONE_BUILDER() {
        return User.builder().username(USERNAME_PASS).email(EMAIL_PASS).build();
    }

    public static User ExpectedDummyUser() {
        return User.builder().userRoles(USER_ROLES()).build();
    }
}
