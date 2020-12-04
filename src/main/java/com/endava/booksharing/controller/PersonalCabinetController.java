package com.endava.booksharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalCabinetController {

    @GetMapping("/personal-cabinet")
    public String showPersonalCabinet() {
        return "personalCab";
    }
}
