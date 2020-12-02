package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.DeleteBookRequestDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.BookService;
import com.endava.booksharing.utils.exceptions.ExceptionsHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookService bookService;
    private final ExceptionsHandler exceptionsHandler;

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(Response.build(bookService.saveBook(bookRequestDto)));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBook(@RequestBody @Valid DeleteBookRequestDto deleteBookRequestDto) {
        return ResponseEntity.ok(Response.build(bookService.deleteBook(deleteBookRequestDto)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(Response.build(bookService.updateBook(id, bookRequestDto)));
    }
}
