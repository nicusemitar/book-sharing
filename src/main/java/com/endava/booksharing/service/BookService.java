package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.DeleteBookRequestDto;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Tags;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.repository.TagsRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;

import static com.endava.booksharing.utils.mappers.BookMapper.mapBookRequestDtoToBook;
import static com.endava.booksharing.utils.mappers.BookMapper.mapBookToBookResponseDto;
import static com.endava.booksharing.utils.mappers.BookMapper.setDeletedBookValues;
import static com.endava.booksharing.utils.mappers.BookMapper.setUserAndTagsForBook;
import static com.endava.booksharing.utils.mappers.BookMapper.updateFromBookRequestDtoToBook;
import static com.endava.booksharing.utils.mappers.TagsMapper.mapTagsRequestDtoToTags;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final TagsRepository tagsRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final ZoneId zoneId = ZoneId.of("Europe/Bucharest");

    @Value("${message.book.not-found}")
    private String messageBookNotFound;

    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            log.warn("Book with with id [{}] was not found in database", id);
            return new NotFoundException(format(messageBookNotFound, id));
        });
        return mapBookToBookResponseDto.apply(book);
    }

    @Transactional
    public BookResponseDto saveBook(BookRequestDto bookRequestDto) {
        log.info("Saving book with title [{}]", bookRequestDto.getTitle());
        final List<Tags> tagsFromDatabase = tagsRepository.findAll();

        Book book = mapBookRequestDtoToBook.apply(bookRequestDto);
        setUserAndTagsForBook(userDetailsService.getCurrentUser(), mapTagsRequestDtoToTags(bookRequestDto.getTagList(), tagsFromDatabase), book);
        book = bookRepository.save(book);

        return mapBookToBookResponseDto.apply(book);
    }

    public BookResponseDto deleteBook(DeleteBookRequestDto deleteBookRequestDto) {
        log.info("Deleting the records for book with id [{}]", deleteBookRequestDto.getBookId());

        Long bookId = deleteBookRequestDto.getBookId();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> {
                    log.warn("Book with id [{}] is not present in database", bookId);
                    return new NotFoundException("Book with id" + bookId + " was not found in database");
                });
        setDeletedBookValues(userDetailsService.getCurrentUser(), deleteBookRequestDto.getDescription(), book);
        book = bookRepository.save(book);

        return mapBookToBookResponseDto.apply(book);
    }

    @Transactional
    public BookResponseDto updateBook(Long bookId, BookRequestDto bookRequestDto) {
        log.info("Updating the records for book with id [{}]", bookId);
        final List<Tags> tagsFromDatabase = tagsRepository.findAll();

        Book toBeUpdatedBook = bookRepository.findById(bookId).orElseThrow(
                () -> {
                    log.warn("Book with id [{}] is not present in database", bookId);
                    return new NotFoundException("Book with id" + bookId + " was not found in database");
                });
        updateFromBookRequestDtoToBook(toBeUpdatedBook, bookRequestDto, tagsFromDatabase);
        toBeUpdatedBook = bookRepository.save(toBeUpdatedBook);

        return mapBookToBookResponseDto.apply(toBeUpdatedBook);
    }
}
