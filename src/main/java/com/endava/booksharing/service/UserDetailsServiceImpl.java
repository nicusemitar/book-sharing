package com.endava.booksharing.service;

import com.endava.booksharing.model.Role;
import com.endava.booksharing.model.User;
import com.endava.booksharing.repository.RoleRepository;
import com.endava.booksharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        log.info("Success: User with username:" + username + " authenticated");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getUserRoles());
    }

    @PreAuthorize("authenticated")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Success: User with username:" + authentication.getName() + " founded");
        return userRepository.getUserByUsername(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException(authentication.getName())
        );
    }
}