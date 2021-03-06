package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.PageableReviewsResponseDto;
import com.endava.booksharing.api.dto.ReviewRequestDto;
import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.model.Review;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.ID_TWO;
import static com.endava.booksharing.TestConstants.REVIEW_TEXT_CORRECT;
import static com.endava.booksharing.TestConstants.USER_ONE_USERNAME;
import static com.endava.booksharing.TestConstants.USER_TWO_USERNAME;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_DELETED_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_TWO;

public class ReviewTestUtils {
    public static final Review REVIEW_ONE = Review
            .builder()
            .id(ID_ONE)
            .textReview(REVIEW_TEXT_CORRECT)
            .book(BOOK_DELETED_ONE)
            .user(USER_ONE)
            .build();
    public static final Review REVIEW_TWO = Review
            .builder()
            .id(ID_TWO)
            .textReview(REVIEW_TEXT_CORRECT)
            .book(BOOK_DELETED_ONE)
            .user(USER_TWO)
            .build();
    public static final Review REVIEW_ONE_NO_ID = Review
            .builder()
            .textReview(REVIEW_TEXT_CORRECT)
            .book(BOOK_DELETED_ONE)
            .user(USER_ONE)
            .build();
    public static final ReviewResponseDto REVIEW_RESPONSE_DTO_ONE = ReviewResponseDto
            .builder()
            .id(ID_ONE)
            .textReview(REVIEW_TEXT_CORRECT)
            .username(USER_ONE_USERNAME)
            .build();
    public static final ReviewResponseDto REVIEW_RESPONSE_DTO_TWO = ReviewResponseDto
            .builder()
            .id(ID_TWO)
            .textReview(REVIEW_TEXT_CORRECT)
            .username(USER_TWO_USERNAME)
            .build();
    public static final ReviewRequestDto REVIEW_REQUEST_DTO = ReviewRequestDto
            .builder()
            .textReview(REVIEW_TEXT_CORRECT)
            .build();

    public static final List<Review> REVIEW_LIST = Arrays.asList(REVIEW_ONE, REVIEW_TWO);
    public static final Pageable PAGEABLE = PageRequest.of(0, 8);
    public static final PageImpl<Review> REVIEW_PAGE = new PageImpl<>(REVIEW_LIST, PAGEABLE, 2L);

    public static final PageableReviewsResponseDto PAGEABLE_REVIEWS_RESPONSE_DTO = new PageableReviewsResponseDto().toBuilder()
            .totalPages(1)
            .totalItems(2)
            .currentPage(0)
            .reviews(Arrays.asList(REVIEW_RESPONSE_DTO_ONE, REVIEW_RESPONSE_DTO_TWO))
            .build();
}
