package com.endava.booksharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BooksController {

    @GetMapping("/all-books")
    public String books() {
        return "allBooksPage";
    }
}
