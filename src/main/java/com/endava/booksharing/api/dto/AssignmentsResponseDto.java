package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AssignmentsResponseDto {
    private Long id;
    private LocalDate assignDate;
    private LocalDate dueDate;
    private String bookName;
    private Long bookId;
}