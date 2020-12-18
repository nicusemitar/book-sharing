package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.TagsResponseDto;
import com.endava.booksharing.model.enums.TagsType;
import com.endava.booksharing.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.endava.booksharing.utils.mappers.TagsMapper.mapTagsToTagsResponseDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagsService {

    private final TagsRepository tagsRepository;

    public List<TagsResponseDto> getTagsByType(String tagType) {
        return tagsRepository.findAllByTagTypeEquals(TagsType.valueOf(tagType.toUpperCase()))
                .stream().map(mapTagsToTagsResponseDto).collect(Collectors.toList());
    }

    public List<TagsResponseDto> getTagsByName(String tagName) {
        return tagsRepository.findAllByTagNameContaining(tagName)
                .stream().map(mapTagsToTagsResponseDto).collect(Collectors.toList());
    }
}
