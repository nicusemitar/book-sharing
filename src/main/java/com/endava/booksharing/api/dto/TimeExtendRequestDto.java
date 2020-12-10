package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TimeExtendRequestDto {

    @NotEmpty(message = "Description can't be empty!")
    @Size(max = 2048, message = "Description size must be between 1 and 2048!")
    private String description;

    @NotEmpty(message = "Requested date can't be empty!")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$", message = "Date format must be 'year-month-date'!")
    private String requestedDate;
}
