package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.TagsResponseDto;
import com.endava.booksharing.model.enums.TagsType;
import com.endava.booksharing.repository.TagsRepository;
import org.checkerframework.checker.i18nformatter.qual.I18nChecksFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.endava.booksharing.utils.TagsTestUtils.TAGS_RESPONSE_DTO;
import static com.endava.booksharing.utils.TagsTestUtils.TAG_WITH_TYPE_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagsServiceTest {

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private TagsService tagsService;

    @Test
    public void shouldReturnTagsResponseDtoByTagType() {
        when(tagsRepository.findAllByTagTypeEquals(any(TagsType.class))).thenReturn(Collections.singletonList(TAG_WITH_TYPE_ONE));

        List<TagsResponseDto> expectedTagsResponseDto = Collections.singletonList(TAGS_RESPONSE_DTO);
        List<TagsResponseDto> actualTagsResponseDto =  tagsService.getTagsByType("CUSTOM");

        assertEquals(expectedTagsResponseDto,actualTagsResponseDto);
    }

    @Test
    public void shouldReturnTagsResponseDtoByTagName() {
        when(tagsRepository.findAllByTagNameContaining(anyString())).thenReturn(Collections.singletonList(TAG_WITH_TYPE_ONE));

        List<TagsResponseDto> expectedTagsResponseDto = Collections.singletonList(TAGS_RESPONSE_DTO);
        List<TagsResponseDto> actualTagsResponseDto =  tagsService.getTagsByName("science");

        assertEquals(expectedTagsResponseDto,actualTagsResponseDto);
    }
}
