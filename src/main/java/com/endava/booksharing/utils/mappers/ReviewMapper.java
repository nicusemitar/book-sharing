package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.ReviewRequestDto;
import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.model.Review;
import lombok.NoArgsConstructor;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReviewMapper {

    public static final Function<Review, ReviewResponseDto> mapReviewToReviewResponseDto = review -> ReviewResponseDto.builder()
            .textReview(review.getTextReview())
            .username(review.getUser().getUsername())
            .build();

    public static final Function<ReviewRequestDto, Review> mapReviewRequestDtoToReview = reviewRequestDto ->
            Review.builder()
                    .textReview(reviewRequestDto.getTextReview())
                    .build();
}
