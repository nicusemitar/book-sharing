package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.TagsRequestDto;
import com.endava.booksharing.model.Author;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Tags;
import com.endava.booksharing.model.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.endava.booksharing.utils.mappers.TagsMapper.mapTagsRequestDtoToTags;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class BookMapper {
    private static final ZoneId zoneId = ZoneId.of("Europe/Bucharest");

    public static void setUserAndTagsForBook(User user, Set<Tags> tags, Book book) {
        book.setUser(user);
        book.setTags(tags);
    }

    public static void setDeletedBookValues(User user, String deletedWhy, Book book) {
        book.setDeletedBy(user);
        book.setDeletedWhy(deletedWhy);
        book.setDeletedAt(LocalDate.now(zoneId));
    }

    public static void updateFromBookRequestDtoToBook(Book book, BookRequestDto bookRequestDto, List<Tags> tagsFromDatabase) {
        log.info("Setting new records for book");

        book.setTitle(bookRequestDto.getTitle());
        book.setPages(bookRequestDto.getPages());
        book.setDescription(bookRequestDto.getDescription());
        book.setBookLanguage(bookRequestDto.getBookLanguage());
        book.setTags(mapTagsRequestDtoToTags(bookRequestDto.getTagList(), tagsFromDatabase));
        book.getAuthor().setFirstName(bookRequestDto.getAuthorFirstName());
        book.getAuthor().setLastName(bookRequestDto.getAuthorLastName());

    }

    public static final Function<BookRequestDto, Book> mapBookRequestDtoToBook = bookRequestDto -> Book.builder()
            .title(bookRequestDto.getTitle())
            .description(bookRequestDto.getDescription())
            .bookLanguage(bookRequestDto.getBookLanguage())
            .pages(bookRequestDto.getPages())
            .author(Author.builder().firstName(bookRequestDto.getAuthorFirstName()).lastName(bookRequestDto.getAuthorLastName()).build())
            .addedAt(LocalDate.now(zoneId))
            .build();

    public static final Function<Book, BookResponseDto> mapBookToBookResponseDto = book -> BookResponseDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .pages(book.getPages())
            .description(book.getDescription())
            .language(book.getBookLanguage())
            .addedAt(book.getAddedAt() != null ? book.getAddedAt().toString() : null)
            .author(book.getAuthor().getFirstName() + " " + (book.getAuthor().getLastName()))
            .addedBy(book.getUser() != null ? book.getUser().getUsername() : null)
            .deletedBy(book.getDeletedBy() != null ? book.getDeletedBy().getUsername() : null)
            .deletedWhy(book.getDeletedWhy() != null ? book.getDeletedWhy() : null)
            .deletedDate(book.getDeletedAt() != null ? book.getDeletedAt().toString() : null)
            .build();
}
