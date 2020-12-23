package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.BooksResponseDto;
import com.endava.booksharing.api.dto.PageableBooksResponseDto;
import com.endava.booksharing.model.Author;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Tags;
import com.endava.booksharing.model.User;
import com.endava.booksharing.model.enums.StatusType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.StringUtils.capitalize;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class BookMapper {
    private static final ZoneId zoneId = ZoneId.of("Europe/Bucharest");

    public static void setUserAndTagsForBook(User user, Set<Tags> tags, Book book) {
        book.setUser(user);
        book.setTags(tags);
        book.setBookStatus(StatusType.FREE);
    }

    public static void setDeletedBookValues(User user, String deletedWhy, Book book) {
        book.setDeletedBy(user);
        book.setDeletedWhy(deletedWhy);
        book.setDeletedAt(LocalDate.now(zoneId));
        book.setBookStatus(StatusType.DELETED);
    }

    public static void updateFromBookRequestDtoToBook(Book book, BookRequestDto bookRequestDto) {
        log.info("Setting new records for book");

        book.setTitle(capitalize(bookRequestDto.getTitle()));
        book.setPages(bookRequestDto.getPages());
        book.setDescription(capitalize(bookRequestDto.getDescription()));
        book.setBookLanguage(capitalize(bookRequestDto.getBookLanguage()));
        book.getAuthor().setFirstName(capitalize(bookRequestDto.getAuthorFirstName()));
        book.getAuthor().setLastName(capitalize(bookRequestDto.getAuthorLastName()));
    }

    public static final Function<BookRequestDto, Book> mapBookRequestDtoToBook = bookRequestDto -> Book.builder()
            .title(bookRequestDto.getTitle())
            .description(capitalize(bookRequestDto.getDescription()))
            .bookLanguage(capitalize(bookRequestDto.getBookLanguage()))
            .pages(bookRequestDto.getPages())
            .author(Author.builder().firstName(capitalize(bookRequestDto.getAuthorFirstName()))
                    .lastName(capitalize(bookRequestDto.getAuthorLastName())).build())
            .addedAt(LocalDate.now(zoneId))
            .build();

    public static final Function<Book, BookResponseDto> mapBookToBookResponseDto = book -> BookResponseDto.builder()
            .id(book.getId())
            .title(capitalize(book.getTitle()))
            .pages(book.getPages())
            .description(capitalize(book.getDescription()))
            .language(capitalize(book.getBookLanguage()))
            .status(capitalize(book.getBookStatus().toString()))
            .addedAt(book.getAddedAt() != null ? book.getAddedAt().toString() : null)
            .author(capitalize(book.getAuthor().getFirstName()) + " " + capitalize(book.getAuthor().getLastName()))
            .addedBy(book.getUser() != null ? book.getUser().getUsername() : null)
            .deletedBy(book.getDeletedBy() != null ? book.getDeletedBy().getUsername() : null)
            .deletedWhy(book.getDeletedWhy() != null ? book.getDeletedWhy() : null)
            .deletedDate(book.getDeletedAt() != null ? book.getDeletedAt().toString() : null)
            .tags(book.getTags().stream().map(Tags::getTagName).collect(Collectors.toSet()))
            .build();

    public static final Function<Book, BooksResponseDto> mapBookToBooksResponseDto = book -> BooksResponseDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .authorName(capitalize(book.getAuthor().getFirstName()) + " " + capitalize(book.getAuthor().getLastName()))
            .language(capitalize(book.getBookLanguage()))
            .status(capitalize(book.getBookStatus().toString()))
            .tags(book.getTags().stream().map(Tags::getTagName).collect(Collectors.toSet()))
            .build();

    public static final Function<Page<BooksResponseDto>, PageableBooksResponseDto> mapBooksResponseDtoPageToPageableBooksResponseDto = booksPage -> PageableBooksResponseDto.builder()
            .books(booksPage.getContent())
            .totalItems(booksPage.getTotalElements())
            .currentPage(booksPage.getNumber())
            .totalPages(booksPage.getTotalPages())
            .build();
}