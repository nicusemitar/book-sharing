package com.endava.booksharing.controller;

import com.endava.booksharing.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ModelAndView showBookDetails(@PathVariable Long id) {
        if (bookService.checkIfBookIsNotDeleted(id).isPresent()) {
            return new ModelAndView("book");
        }
        return new ModelAndView("redirect:/all-books");
    }
}