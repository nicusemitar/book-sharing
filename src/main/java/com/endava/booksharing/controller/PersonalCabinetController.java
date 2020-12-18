package com.endava.booksharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonalCabinetController {

    @GetMapping("/personal-cabinet")
    public String showPersonalCabinet() {
        return "personalCab";
    }

    @GetMapping("/admin-page")
    public ModelAndView showBookDetails() {
        return new ModelAndView("admin");
    }
}
