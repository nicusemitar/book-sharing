package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookResponseDto {
    private Long id;
    private String title;
    private Long pages;
    private String description;
    private String language;
    private String addedAt;
    private String author;
    private String addedBy;
    private String deletedBy;
    private String deletedWhy;
    private String deletedDate;
    private String status;
    private Set<String> tags;
    private String bookImageUrl;
}