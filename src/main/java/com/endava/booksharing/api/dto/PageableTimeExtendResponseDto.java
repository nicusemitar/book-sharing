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
public class PageableTimeExtendResponseDto {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private List<TimeExtendResponseDto> timeExtendResponseDtoList;
}