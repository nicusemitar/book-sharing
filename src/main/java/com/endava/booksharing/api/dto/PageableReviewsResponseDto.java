package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PageableReviewsResponseDto {
    public long totalItems;
    public int totalPages;
    public int currentPage;
    public List<ReviewResponseDto> reviews;
}
