package com.endava.booksharing.service;


import com.endava.booksharing.repository.RoleRepository;
import com.endava.booksharing.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.endava.booksharing.utils.UserTestUtils.USER_DETAILS;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    private Authentication auth;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;


    @Test
    @WithMockUser("usernameone")
    public void shouldReturnUser() {
        UserDetails expectedUserDetails = USER_DETAILS;

        when(userRepository.getUserByUsername(USER_ONE.getUsername())).thenReturn(Optional.of(USER_ONE));

        UserDetails returnedUserDetails = userDetailsService.loadUserByUsername(USER_ONE.getUsername());

        assertEquals(returnedUserDetails.getUsername(), expectedUserDetails.getUsername());

        verify(userRepository).getUserByUsername(USER_ONE.getUsername());
    }


    @Test
    public void shouldReturnUserNotFoundException() {
        when(userRepository.getUserByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(USER_ONE.getUsername());
        });
    }


    @Test
    public void shouldReturnUserNotFoundExceptionForCurrentUser() {
        when(auth.getName()).thenReturn(USER_ONE.getUsername());
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.getUserByUsername(USER_ONE.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.getCurrentUser();
        });
        SecurityContextHolder.clearContext();
    }

}
