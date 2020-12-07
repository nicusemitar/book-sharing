package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.DeleteBookRequestDto;
import com.endava.booksharing.model.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mockito.internal.util.collections.Sets;

import java.util.Collections;

import static com.endava.booksharing.TestConstants.*;
import static com.endava.booksharing.utils.AuthorTestUtils.AUTHOR_ONE;
import static com.endava.booksharing.utils.TagsTestUtils.DEFAULT_TAG;
import static com.endava.booksharing.utils.TagsTestUtils.TAGS_REQUEST_DTO;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_TWO;
import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE;
import static com.endava.booksharing.TestConstants.BOOK_PAGES;
import static com.endava.booksharing.TestConstants.BOOK_DESCRIPTION;
import static com.endava.booksharing.TestConstants.BOOK_ADDED_AT_DATE;
import static com.endava.booksharing.TestConstants.BOOK_LANGUAGE;
import static com.endava.booksharing.TestConstants.BOOK_DELETED_AT_DATE;
import static com.endava.booksharing.TestConstants.BOOK_DELETED_WHY;
import static com.endava.booksharing.TestConstants.ID_TWO;
import static com.endava.booksharing.TestConstants.BOOK_ADDED_AT;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME;
import static com.endava.booksharing.TestConstants.USER_ONE_USERNAME;
import static com.endava.booksharing.TestConstants.BOOK_DELETED_AT;
import static com.endava.booksharing.TestConstants.USER_TWO_USERNAME;
import static com.endava.booksharing.utils.AuthorTestUtils.AUTHOR_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_TWO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookTestUtils {
    public static final Book BOOK_DELETED = Book
            .builder()
            .id(ID_ONE)
            .title(BOOK_TITLE)
            .pages(BOOK_PAGES)
            .description(BOOK_DESCRIPTION)
            .addedAt(BOOK_ADDED_AT_DATE)
            .bookLanguage(BOOK_LANGUAGE)
            .deletedAt(BOOK_DELETED_AT_DATE)
            .deletedWhy(BOOK_DELETED_WHY)
            .author(AUTHOR_ONE)
            .user(USER_ONE)
            .deletedBy(USER_ONE)
            .build();
    public static final Book BOOK_NOT_DELETED = Book
            .builder()
            .id(ID_TWO)
            .title(BOOK_TITLE)
            .pages(BOOK_PAGES)
            .description(BOOK_DESCRIPTION)
            .addedAt(BOOK_ADDED_AT_DATE)
            .bookLanguage(BOOK_LANGUAGE)
            .author(AUTHOR_ONE)
            .user(USER_TWO)
            .build();
    public static final BookResponseDto BOOK_DELETED_RESPONSE_DTO = BookResponseDto
            .builder()
            .id(ID_ONE)
            .title(BOOK_TITLE)
            .pages(BOOK_PAGES)
            .description(BOOK_DESCRIPTION)
            .language(BOOK_LANGUAGE)
            .addedAt(BOOK_ADDED_AT)
            .author(AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME)
            .addedBy(USER_ONE_USERNAME)
            .deletedBy(USER_ONE_USERNAME)
            .deletedWhy(BOOK_DELETED_WHY)
            .deletedDate(BOOK_DELETED_AT)
            .build();
    public static final BookResponseDto BOOK_NOT_DELETED_RESPONSE_DTO = BookResponseDto
            .builder()
            .id(ID_TWO)
            .title(BOOK_TITLE)
            .pages(BOOK_PAGES)
            .description(BOOK_DESCRIPTION)
            .language(BOOK_LANGUAGE)
            .addedAt(BOOK_ADDED_AT)
            .author(AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME)
            .addedBy(USER_TWO_USERNAME)
            .build();
    public static final BookResponseDto BOOK_RESPONSE_DTO = BookResponseDto.builder()
            .author(AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME)
            .title(BOOK_TITLE)
            .addedBy(USER_ONE_USERNAME)
            .build();
    public static final BookResponseDto UPDATED_BOOK_RESPONSE_DTO = BookResponseDto.builder()
            .author(AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME)
            .title(BOOK_TITLE_UPDATED)
            .build();
    public static final BookRequestDto BOOK_REQUEST_DTO = BookRequestDto.builder()
            .title(BOOK_TITLE)
            .authorFirstName(AUTHOR_FIRST_NAME)
            .authorLastName(AUTHOR_LAST_NAME)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tagList(Collections.singletonList(TAGS_REQUEST_DTO))
            .description(BOOK_DESCRIPTION)
            .build();
    public static final BookRequestDto TO_UPDATE_BOOK_REQUEST_DTO = BookRequestDto.builder()
            .title(BOOK_TITLE_UPDATED)
            .authorFirstName(AUTHOR_FIRST_NAME)
            .authorLastName(AUTHOR_LAST_NAME)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tagList(Collections.singletonList(TAGS_REQUEST_DTO))
            .description(BOOK_DESCRIPTION)
            .build();
    public static final Book BOOK_ONE = Book.builder()
            .title(BOOK_TITLE)
            .author(AUTHOR_ONE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tags(Sets.newSet(DEFAULT_TAG))
            .description(BOOK_DESCRIPTION)
            .id(ID_ONE)
            .build();
    public static final Book BOOK_TWO = Book.builder()
            .title(BOOK_TITLE)
            .author(AUTHOR_ONE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tags(Sets.newSet(DEFAULT_TAG))
            .description(BOOK_DESCRIPTION)
            .addedAt(DEFAULT_DATE)
            .user(USER_ONE)
            .id(ID_ONE)
            .build();
    public static final Book BOOK_ONE_UPDATED = Book.builder()
            .title(BOOK_TITLE_UPDATED)
            .author(AUTHOR_ONE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tags(Sets.newSet(DEFAULT_TAG))
            .description(BOOK_DESCRIPTION)
            .id(ID_ONE)
            .build();
    public static final Book DELETED_BOOK = Book.builder()
            .title(BOOK_TITLE)
            .author(AUTHOR_ONE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tags(Sets.newSet(DEFAULT_TAG))
            .description(BOOK_DESCRIPTION)
            .id(ID_ONE)
            .deletedAt(DEFAULT_DATE)
            .deletedWhy(BOOK_DESCRIPTION)
            .deletedBy(USER_ONE)
            .build();
    public static final DeleteBookRequestDto DELETE_BOOK_REQUEST_DTO = DeleteBookRequestDto.builder()
            .bookId(ID_ONE)
            .description(BOOK_DESCRIPTION)
            .build();
    public static final BookResponseDto DELETED_BOOK_RESPONSE_DTO = BookResponseDto.builder()
            .author(AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME)
            .title(BOOK_TITLE)
            .deletedBy(USER_ONE_USERNAME)
            .deletedDate(DEFAULT_DATE.toString())
            .deletedWhy(BOOK_DESCRIPTION)
            .build();
}
