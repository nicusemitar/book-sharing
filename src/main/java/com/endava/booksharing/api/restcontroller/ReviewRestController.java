package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.ReviewRequestDto;
import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ReviewRestController {

    public final ReviewService reviewService;

    @GetMapping("/books/{id}/review")
    public ResponseEntity<Response<List<ReviewResponseDto>>> getAllReviewsByBookIdDesc(@PathVariable Long id) {
        return ResponseEntity.ok().body(Response.build(reviewService.getAllReviewsByBookIdDesc(id)));
    }

    @PostMapping("/books/{id}/review")
    public ResponseEntity<Response<ReviewResponseDto>> addReview(@PathVariable Long id,
                                                                 @RequestBody @Valid ReviewRequestDto reviewRequestDto, Errors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new ValidationException(Objects.requireNonNull(validationErrors.getFieldError()).getDefaultMessage());
        }
        return ResponseEntity.ok().body(Response.build(reviewService.save(reviewRequestDto, id)));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Response> deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().body(Response.build("Review with id " + id + " was deleted successfully!"));
    }

    @GetMapping("/reviews")
    public ResponseEntity<Object> getReviews(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "8", name = "size") int size) {
        if (page < 0 || size <= 0) throw new IllegalArgumentException();

        return ResponseEntity.ok(reviewService.getAllReviews(page, size));
    }
}
