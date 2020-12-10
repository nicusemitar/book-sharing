package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TimeExtendResponseDto {
    private String username;
    private Long bookId;
    private String description;
    private String dueDate;
    private String requestedDate;
}
