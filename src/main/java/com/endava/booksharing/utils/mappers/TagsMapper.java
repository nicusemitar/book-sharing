package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.TagsRequestDto;
import com.endava.booksharing.api.dto.TagsResponseDto;
import com.endava.booksharing.model.Tags;
import com.endava.booksharing.model.enums.TagsType;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TagsMapper {
    public static Set<Tags> mapTagsRequestDtoToTags(List<TagsRequestDto> list, List<Tags> tagsFromDatabase) {
        Set<Tags> newBookTags = new HashSet<>();

        List<TagsRequestDto> customTagsWithAnotherTagTypeInDB = list.stream()
                .filter(list1 -> tagsFromDatabase.stream().anyMatch
                        (tags -> list1.getTagName().equalsIgnoreCase(tags.getTagName())
                                && !list1.getTagType().equalsIgnoreCase(tags.getTagType().toString())
                                && list1.getTagType().equals(TagsType.CUSTOM.toString())))
                .collect(Collectors.toList());

        List<TagsRequestDto> filteredListWithTags = new ArrayList<>(list);
        filteredListWithTags.removeIf(customTagsWithAnotherTagTypeInDB::contains);

        filteredListWithTags.forEach(tagsRequestDto -> newBookTags.add(
                tagsFromDatabase.stream().filter
                        (tags -> tagsRequestDto.getTagName().equalsIgnoreCase(tags.getTagName()))
                        .findFirst().orElse(Tags.builder().tagName(tagsRequestDto.getTagName().toLowerCase())
                        .tagType(TagsType.valueOf(tagsRequestDto.getTagType())).build())));
        return newBookTags;
    }

    public static final Function<Tags, TagsResponseDto> mapTagsToTagsResponseDto = tags -> TagsResponseDto.builder()
            .tagName(tags.getTagName())
            .tagType(tags.getTagType().toString())
            .build();
}
