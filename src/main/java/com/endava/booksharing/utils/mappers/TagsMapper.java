package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.TagsRequestDto;
import com.endava.booksharing.model.Tags;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TagsMapper {
    public static Set<Tags> mapTagsRequestDtoToTags(List<TagsRequestDto> list, List<Tags> tagsFromDatabase) {
        Set<Tags> newBookTags = new HashSet<>();

        list.forEach(tagsRequestDto -> newBookTags.add(
                tagsFromDatabase.stream().filter
                        (tags -> tagsRequestDto.getTagName().equals(tags.getTagName())).findFirst()
                        .orElse(Tags.builder().tagName(tagsRequestDto.getTagName()).build())));
        return newBookTags;
    }
}
