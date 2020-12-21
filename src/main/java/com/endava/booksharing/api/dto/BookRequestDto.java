package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Pattern(regexp = "(?i)[A-z0-9]([- ',.A-z0-9]{0,40}[A-z0-9])$", message = "Title should not contain invalid characters!")
    private String title;

    @Min(1)
    @Max(5000)
    private Long pages;

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]{1,30}$", message = "Language field should not contain numbers")
    private String bookLanguage;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?i)[A-z]([- ',.A-z]{0,23}[A-z])$", message = "Invalid author first name")
    private String authorFirstName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?i)[A-z]([- ',.A-z]{0,23}[A-z])$", message = "Invalid author last name")
    private String authorLastName;

    private List<TagsRequestDto> tagList;

    private String bookImageUrl;
}