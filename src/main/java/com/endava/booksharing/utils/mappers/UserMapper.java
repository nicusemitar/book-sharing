package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.UserRegistrationRequestDto;
import com.endava.booksharing.api.dto.UserRegistrationResponseDto;
import com.endava.booksharing.model.User;
import lombok.NoArgsConstructor;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper {

    public static final Function<User, UserRegistrationResponseDto> mapUserToUserResponseDto = user -> UserRegistrationResponseDto.builder()
            .email(user.getEmail())
            .username(user.getUsername())
            .build();

    public static final Function<UserRegistrationRequestDto, User> mapUserRequestDtoToUser = userRequestDto -> User.builder()
            .username(userRequestDto.getUsername())
            .password(userRequestDto.getPassword())
            .email(userRequestDto.getEmail())
            .build();
}
