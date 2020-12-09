package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.TagsRequestDto;
import com.endava.booksharing.api.dto.TagsResponseDto;
import com.endava.booksharing.model.Tags;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.endava.booksharing.TestConstants.TAG_BUSY;
import static com.endava.booksharing.TestConstants.TAGS_TYPE_ONE;
import static com.endava.booksharing.TestConstants.TAG_ONE;
import static com.endava.booksharing.TestConstants.TAG_TYPE_NAME_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagsTestUtils {
    public static final Tags TAGS_BUSY = Tags.builder().tagName(TAG_BUSY).build();
    public static final Tags DEFAULT_TAG = Tags.builder().tagName(TAG_ONE).tagType(TAGS_TYPE_ONE).build();
    public static final TagsRequestDto TAGS_REQUEST_DTO = TagsRequestDto.builder().tagName(TAG_ONE).tagType(TAG_TYPE_NAME_ONE).build();
    public static final TagsResponseDto TAGS_RESPONSE_DTO = TagsResponseDto.builder().tagName(TAG_ONE).tagType(TAGS_TYPE_ONE.toString()).build();
    public static final Tags TAG_WITH_TYPE_ONE = Tags.builder().tagName(TAG_ONE).tagType(TAGS_TYPE_ONE).build();
}