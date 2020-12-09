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
public class BooksResponseDto {
    private Long id;
    private String title;
    private String authorName;
    private String language;
    private Set<String> tags;
}
