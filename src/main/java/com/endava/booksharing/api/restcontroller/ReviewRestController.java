package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.ReviewRequestDto;
import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class ReviewRestController {

    public final ReviewService reviewService;

    @GetMapping("/{id}/review")
    public ResponseEntity<Response<List<ReviewResponseDto>>> getAllReviews(@PathVariable Long id) {
        return ResponseEntity.ok().body(Response.build(reviewService.getAllReviews(id)));
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Response<ReviewResponseDto>> addReview(@PathVariable Long id,
            @RequestBody @Valid ReviewRequestDto reviewRequestDto, Errors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new ValidationException(Objects.requireNonNull(validationErrors.getFieldError()).getDefaultMessage());
        }
        return ResponseEntity.ok().body(Response.build(reviewService.save(reviewRequestDto, id)));
    }
}
