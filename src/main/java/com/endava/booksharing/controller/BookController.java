package com.endava.booksharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/book")
public class BookController {

    @GetMapping("/{id}")
    public ModelAndView showBookDetails() {
        return new ModelAndView("book");
    }
}