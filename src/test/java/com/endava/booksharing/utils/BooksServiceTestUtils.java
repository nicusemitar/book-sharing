package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.BooksResponseDto;
import com.endava.booksharing.api.dto.PageableBooksResponseDto;
import com.endava.booksharing.model.Author;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Tags;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.endava.booksharing.TestConstants.ADDED_AT_DATE;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME_TWO;
import static com.endava.booksharing.TestConstants.AUTHOR_FULL_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_FULL_NAME_TWO;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME_TWO;
import static com.endava.booksharing.TestConstants.BOOK_PAGES_NUMBER_ONE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_ONE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_TWO;
import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.ID_TWO;
import static com.endava.booksharing.TestConstants.RUSSIAN_LANGUAGE;

public class BooksServiceTestUtils {
   public static List<Tags> tagsList = Arrays.asList(new Tags(ID_ONE,"adventure",Collections.EMPTY_SET),
           new Tags(ID_TWO,"science",Collections.EMPTY_SET));

   static List<String> tagsStringList = Arrays.asList("science", "adventure");
   public static  Set<String> TAGS_STRING_SET= new HashSet<>(tagsStringList);
   public static final Set<Tags> TAGS_SET = new HashSet<>(tagsList);

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

   public static final Book BOOK_ONE = Book.builder()
            .id(ID_ONE)
            .author(AUTHOR_ONE)
            .addedAt(ADDED_AT_DATE)
            .bookLanguage(RUSSIAN_LANGUAGE)
            .pages(BOOK_PAGES_NUMBER_ONE)
            .title(BOOK_TITLE_ONE)
            .tags(TAGS_SET)
            .build();

      public static final Book BOOK_TWO = Book.builder()
            .id(ID_TWO)
            .author(AUTHOR_TWO)
            .addedAt(ADDED_AT_DATE)
            .bookLanguage(RUSSIAN_LANGUAGE)
            .pages(BOOK_PAGES_NUMBER_ONE)
            .title(BOOK_TITLE_TWO)
            .tags(TAGS_SET)
            .build();

      public static final BooksResponseDto BOOKS_RESPONSE_DTO_ONE = BooksResponseDto.builder()
              .id(ID_ONE)
              .title(BOOK_TITLE_ONE)
              .authorName(AUTHOR_FULL_NAME_ONE)
              .language(RUSSIAN_LANGUAGE)
              .tags(TAGS_STRING_SET)
              .build();

       public static final BooksResponseDto BOOKS_RESPONSE_DTO_TWO = BooksResponseDto.builder()
              .id(ID_TWO)
              .title(BOOK_TITLE_TWO)
              .authorName(AUTHOR_FULL_NAME_TWO)
              .language(RUSSIAN_LANGUAGE)
              .tags(TAGS_STRING_SET)
              .build();
      public static final List<BooksResponseDto> BOOKS_RESPONSE_DTO_LIST =
              Arrays.asList(BOOKS_RESPONSE_DTO_ONE, BOOKS_RESPONSE_DTO_TWO);

      public static final List<Book> BOOK_LIST =  Arrays.asList(BOOK_ONE,BOOK_TWO);
      public static final Pageable PAGEABLE = PageRequest.of(0,15);
      public static final PageImpl<Book> BOOK_PAGE = new PageImpl<>(BOOK_LIST, PAGEABLE,2L);
      public static final PageImpl<BooksResponseDto> BOOKS_RESPONSE_DTO_PAGE = new PageImpl<>(BOOKS_RESPONSE_DTO_LIST,PAGEABLE,2L);

      public static final PageableBooksResponseDto PAGEABLE_BOOKS_RESPONSE_DTO = new PageableBooksResponseDto().toBuilder()
              .totalPages(1)
              .totalItems(2)
              .currentPage(0)
              .books( Arrays.asList(BOOKS_RESPONSE_DTO_ONE, BOOKS_RESPONSE_DTO_TWO))
              .build();

}

