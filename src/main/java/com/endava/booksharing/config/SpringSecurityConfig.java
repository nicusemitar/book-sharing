package com.endava.booksharing.config;

import com.endava.booksharing.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    String[] staticResources = {
            "/resources/**",
            "/static/**",
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/scripts/**"
    };

    @Override
    public void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/register", "/users").permitAll()
                .antMatchers("/all-books", "/books", "/books/filter", "/personal-cabinet", "/assignments", "/assignments/current-user", "/extends").hasAuthority("USER")
                .antMatchers("/admin-page", "/assignments/extends/**").hasAuthority("ADMIN")
                .antMatchers("/book/").hasAnyAuthority()
                .antMatchers(HttpMethod.DELETE, "/reviews/**", "/admin-page").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/all-books", true)
                .failureForwardUrl("/login?error")
                .and()
                .logout().logoutSuccessUrl("/login").permitAll()
                .permitAll().and().httpBasic().and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

