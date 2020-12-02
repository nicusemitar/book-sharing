package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ReviewRequestDto {

    @NotBlank(message = "Review cannot be blank")
    @Size(min = 1, max = 2048, message = "Review size must be between 1 and 2048")
    private String textReview;
}
