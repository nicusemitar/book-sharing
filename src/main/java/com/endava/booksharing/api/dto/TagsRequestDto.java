package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TagsRequestDto {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Invalid tags!")
    private String tagName;
}