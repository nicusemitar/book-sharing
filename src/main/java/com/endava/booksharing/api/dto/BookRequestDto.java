package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookRequestDto {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?i)[A-z]([- ',.a-z]{5,40}[a-z])", message = "Title should not contain invalid characters!")
    private String title;

    @NotNull
    private Long pages;

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Language field should not contain numbers")
    private String bookLanguage;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?i)[a-z]([- ',.a-z]{0,23}[a-z])",message = "Invalid author first name")
    private String authorFirstName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?i)[a-z]([- ',.a-z]{0,23}[a-z])",message = "Invalid author last name")
    private String authorLastName;

    private List<TagsRequestDto> tagList;
}
