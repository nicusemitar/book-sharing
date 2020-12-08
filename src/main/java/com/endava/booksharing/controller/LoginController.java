package com.endava.booksharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return new ModelAndView("login");
        }
        return new ModelAndView("redirect:/all-books");
    }
}
