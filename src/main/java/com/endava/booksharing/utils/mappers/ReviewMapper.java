package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.PageableReviewsResponseDto;
import com.endava.booksharing.api.dto.ReviewRequestDto;
import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.model.Review;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReviewMapper {

    public static final Function<Review, ReviewResponseDto> mapReviewToReviewResponseDto = review -> ReviewResponseDto.builder()
            .textReview(review.getTextReview())
            .username(review.getUser().getUsername())
            .id(review.getId())
            .build();

    public static final Function<ReviewRequestDto, Review> mapReviewRequestDtoToReview = reviewRequestDto ->
            Review.builder()
                    .textReview(reviewRequestDto.getTextReview())
                    .build();

    public static final Function<Page<ReviewResponseDto>, PageableReviewsResponseDto>
            mapReviewResponseDtoPageToPageableReviewsResponseDto = reviewsPage -> PageableReviewsResponseDto.builder()
            .reviews(reviewsPage.getContent())
            .totalItems(reviewsPage.getTotalElements())
            .currentPage(reviewsPage.getNumber())
            .totalPages(reviewsPage.getTotalPages())
            .build();
}
