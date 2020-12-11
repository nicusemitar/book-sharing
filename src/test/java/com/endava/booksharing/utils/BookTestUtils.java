package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.BooksResponseDto;
import com.endava.booksharing.api.dto.DeleteBookRequestDto;
import com.endava.booksharing.api.dto.PageableBooksResponseDto;
import com.endava.booksharing.model.Author;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Tags;
import com.endava.booksharing.model.enums.StatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mockito.internal.util.collections.Sets;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.endava.booksharing.TestConstants.ADDED_AT_DATE;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME_TWO;
import static com.endava.booksharing.TestConstants.AUTHOR_FULL_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_FULL_NAME_TWO;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME_TWO;
import static com.endava.booksharing.TestConstants.BOOK_ADDED_AT;
import static com.endava.booksharing.TestConstants.BOOK_ADDED_AT_DATE;
import static com.endava.booksharing.TestConstants.BOOK_DELETED_AT;
import static com.endava.booksharing.TestConstants.BOOK_DELETED_AT_DATE;
import static com.endava.booksharing.TestConstants.BOOK_DELETED_WHY;
import static com.endava.booksharing.TestConstants.BOOK_DESCRIPTION;
import static com.endava.booksharing.TestConstants.BOOK_LANGUAGE;
import static com.endava.booksharing.TestConstants.BOOK_PAGES;
import static com.endava.booksharing.TestConstants.BOOK_PAGES_NUMBER_ONE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_ONE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_TWO;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_UPDATED;
import static com.endava.booksharing.TestConstants.DEFAULT_DATE;
import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.ID_TWO;
import static com.endava.booksharing.TestConstants.STATUS_DELETED;
import static com.endava.booksharing.TestConstants.STATUS_FREE;
import static com.endava.booksharing.TestConstants.USER_ONE_USERNAME;
import static com.endava.booksharing.TestConstants.USER_TWO_USERNAME;
import static com.endava.booksharing.utils.TagsTestUtils.DEFAULT_TAG;
import static com.endava.booksharing.utils.TagsTestUtils.TAGS_REQUEST_DTO;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_TWO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookTestUtils {
    public static final Author AUTHOR_ONE = Author.builder()
            .id(ID_ONE)
            .firstName(AUTHOR_FIRST_NAME_ONE)
            .lastName(AUTHOR_LAST_NAME_ONE)
            .books(Collections.EMPTY_SET)
            .build();

    public static final Author AUTHOR_TWO = Author.builder()
            .id(ID_ONE)
            .firstName(AUTHOR_FIRST_NAME_TWO)
            .lastName(AUTHOR_LAST_NAME_TWO)
            .books(Collections.EMPTY_SET)
            .build();

    public static final Book BOOK_DELETED_ONE = Book
            .builder()
            .id(ID_ONE)
            .title(BOOK_TITLE)
            .pages(BOOK_PAGES)
            .description(BOOK_DESCRIPTION)
            .addedAt(BOOK_ADDED_AT_DATE)
            .bookLanguage(BOOK_LANGUAGE)
            .deletedAt(BOOK_DELETED_AT_DATE)
            .deletedWhy(BOOK_DELETED_WHY)
            .author(AUTHOR_TWO)
            .user(USER_ONE)
            .bookStatus(STATUS_DELETED)
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
            .bookStatus(STATUS_FREE)
            .author(AUTHOR_TWO)
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
            .author(AUTHOR_FIRST_NAME_TWO + " " + AUTHOR_LAST_NAME_TWO)
            .addedBy(USER_ONE_USERNAME)
            .status(STATUS_DELETED.toString())
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
            .author(AUTHOR_FIRST_NAME_TWO + " " + AUTHOR_LAST_NAME_TWO)
            .status(STATUS_FREE.toString())
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
            .bookStatus(STATUS_FREE)
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
            .bookStatus(STATUS_FREE)
            .id(ID_ONE)
            .build();

    public static final Book BOOK_ONE_UPDATED = Book.builder()
            .title(BOOK_TITLE_UPDATED)
            .author(AUTHOR_ONE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tags(Sets.newSet(DEFAULT_TAG))
            .description(BOOK_DESCRIPTION)
            .bookStatus(STATUS_FREE)
            .id(ID_ONE)
            .build();

    public static final Book DELETED_BOOK = Book.builder()
            .title(BOOK_TITLE)
            .author(AUTHOR_TWO)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES)
            .tags(Sets.newSet(DEFAULT_TAG))
            .description(BOOK_DESCRIPTION)
            .id(ID_ONE)
            .bookStatus(STATUS_DELETED)
            .deletedAt(DEFAULT_DATE)
            .deletedWhy(BOOK_DESCRIPTION)
            .deletedBy(USER_ONE)
            .build();
    public static final DeleteBookRequestDto DELETE_BOOK_REQUEST_DTO = DeleteBookRequestDto.builder()
            .bookId(ID_ONE)
            .description(BOOK_DESCRIPTION)
            .build();
    public static final BookResponseDto DELETED_BOOK_RESPONSE_DTO = BookResponseDto.builder()
            .author(AUTHOR_FIRST_NAME_TWO + " " + AUTHOR_LAST_NAME_TWO)
            .title(BOOK_TITLE)
            .status(STATUS_DELETED.toString())
            .deletedBy(USER_ONE_USERNAME)
            .deletedDate(DEFAULT_DATE.toString())
            .deletedWhy(BOOK_DESCRIPTION)
            .build();
    public static List<Tags> tagsList = Arrays.asList(new Tags(ID_ONE, "adventure", Collections.EMPTY_SET),
            new Tags(ID_TWO, "science", Collections.EMPTY_SET));

    static List<String> tagsStringList = Arrays.asList("science", "adventure");
    public static Set<String> TAGS_STRING_SET = new HashSet<>(tagsStringList);
    public static final Set<Tags> TAGS_SET = new HashSet<>(tagsList);

    public static final Book BOOK_THREE = Book.builder()
            .id(ID_ONE)
            .author(AUTHOR_ONE)
            .addedAt(ADDED_AT_DATE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES_NUMBER_ONE)
            .title(BOOK_TITLE_ONE)
            .bookStatus(STATUS_FREE)
            .tags(TAGS_SET)
            .build();

    public static final Book BOOK_FOUR = Book.builder()
            .id(ID_TWO)
            .author(AUTHOR_TWO)
            .addedAt(ADDED_AT_DATE)
            .bookLanguage(BOOK_LANGUAGE)
            .pages(BOOK_PAGES_NUMBER_ONE)
            .title(BOOK_TITLE_TWO)
            .tags(TAGS_SET)
            .bookStatus(STATUS_FREE)
            .build();

    public static final BooksResponseDto BOOKS_RESPONSE_DTO_ONE = BooksResponseDto.builder()
            .id(ID_ONE)
            .title(BOOK_TITLE_ONE)
            .authorName(AUTHOR_FULL_NAME_ONE)
            .language(BOOK_LANGUAGE)
            .status(STATUS_FREE.toString())
            .tags(TAGS_STRING_SET)
            .build();

    public static final BooksResponseDto BOOKS_RESPONSE_DTO_TWO = BooksResponseDto.builder()
            .id(ID_TWO)
            .title(BOOK_TITLE_TWO)
            .authorName(AUTHOR_FULL_NAME_TWO)
            .language(BOOK_LANGUAGE)
            .status(STATUS_FREE.toString())
            .tags(TAGS_STRING_SET)
            .build();

    public static final List<Book> BOOK_LIST = Arrays.asList(BOOK_THREE, BOOK_FOUR);
    public static final Pageable PAGEABLE = PageRequest.of(0, 15);
    public static final PageImpl<Book> BOOK_PAGE = new PageImpl<>(BOOK_LIST, PAGEABLE, 2L);

    public static final PageableBooksResponseDto PAGEABLE_BOOKS_RESPONSE_DTO = new PageableBooksResponseDto().toBuilder()
            .totalPages(1)
            .totalItems(2)
            .currentPage(0)
            .books(Arrays.asList(BOOKS_RESPONSE_DTO_ONE, BOOKS_RESPONSE_DTO_TWO))
            .build();

}