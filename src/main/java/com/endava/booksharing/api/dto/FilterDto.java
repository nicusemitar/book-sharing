package com.endava.booksharing.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FilterDto {
    private String authorName;
    private String language;
    private Set<String> genTags;
    private Set<String> tags;
    private String tagsFind;
    private String status;
    @Min(value = 0, message = "Page number should not be less than 0")
    private int page;
    @Min(value = 0, message = "Size should not be less than 0")
    private int size;
    private String sort;
}
