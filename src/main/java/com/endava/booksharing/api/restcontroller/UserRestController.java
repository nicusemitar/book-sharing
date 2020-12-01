package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.UserRegistrationRequestDto;
import com.endava.booksharing.api.dto.UserRegistrationResponseDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.UserService;
import com.endava.booksharing.utils.exceptions.ExceptionsHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
    private final ExceptionsHandler exceptionsHandler;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserRegistrationRequestDto userRegistrationRequestDto) {
        UserRegistrationResponseDto userRegistrationResponseDto = userService.saveUser(userRegistrationRequestDto);
        return ResponseEntity.ok().body(Response.build(userRegistrationResponseDto));
    }
}
