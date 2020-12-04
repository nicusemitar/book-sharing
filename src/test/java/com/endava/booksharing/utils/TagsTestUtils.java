package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.TagsRequestDto;
import com.endava.booksharing.model.Tags;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.endava.booksharing.TestConstants.TAG_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagsTestUtils {
    public static final Tags DEFAULT_TAG = Tags.builder().tagName(TAG_ONE).build();
    public static final TagsRequestDto TAGS_REQUEST_DTO = TagsRequestDto.builder().tagName(TAG_ONE).build();
}