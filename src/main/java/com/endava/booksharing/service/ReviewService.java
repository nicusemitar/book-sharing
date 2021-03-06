package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.PageableReviewsResponseDto;
import com.endava.booksharing.api.dto.ReviewRequestDto;
import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Review;
import com.endava.booksharing.model.User;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.repository.ReviewRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.endava.booksharing.utils.mappers.ReviewMapper.mapReviewRequestDtoToReview;
import static com.endava.booksharing.utils.mappers.ReviewMapper.mapReviewResponseDtoPageToPageableReviewsResponseDto;
import static com.endava.booksharing.utils.mappers.ReviewMapper.mapReviewToReviewResponseDto;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${message.book.not-found}")
    private String messageBookNotFound;

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviewsByBookIdDesc(Long bookId) {
        return reviewRepository.getAllReviewsByBookIdDesc(bookId)
                .stream()
                .map(mapReviewToReviewResponseDto)
                .collect(toList());
    }

    @Transactional
    public ReviewResponseDto save(ReviewRequestDto reviewRequestDto, Long bookId) {
        log.info("Saving review for book with: id [{}]", bookId);
        User user = userDetailsService.getCurrentUser();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> {
                    log.warn("Book with id [{}] was not found in the database", bookId);
                    return new NotFoundException(format(messageBookNotFound, bookId));
                });
        Review review = mapReviewRequestDtoToReview.apply(reviewRequestDto);
        review.setBook(book);
        review.setUser(user);
        review = reviewRepository.save(review);
        return mapReviewToReviewResponseDto.apply(review);
    }

    public void deleteReview(Long id) {
        log.info("Deleting the review with id [{}]", id);

        reviewRepository.findById(id).orElseThrow(() -> {
            log.warn("Review with id [{}] is not present in database", id);
            return new NotFoundException("Review with id " + id + " was not found in database");
        });
        reviewRepository.deleteById(id);
    }

    public PageableReviewsResponseDto getAllReviews(int page, int size) {
        Page<ReviewResponseDto> reviewResponseDto = reviewRepository.findAll(
                PageRequest.of(page, size, Sort.by("id").descending()))
                .map(mapReviewToReviewResponseDto);

        return mapReviewResponseDtoPageToPageableReviewsResponseDto.apply(reviewResponseDto);
    }
}
