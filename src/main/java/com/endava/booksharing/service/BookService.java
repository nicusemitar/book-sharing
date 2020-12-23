package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.BookRequestDto;
import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.BooksResponseDto;
import com.endava.booksharing.api.dto.DeleteBookRequestDto;
import com.endava.booksharing.api.dto.PageableBooksResponseDto;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.Tags;
import com.endava.booksharing.model.enums.StatusType;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.repository.TagsRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.endava.booksharing.utils.mappers.BookMapper.mapBookRequestDtoToBook;
import static com.endava.booksharing.utils.mappers.BookMapper.mapBookToBookResponseDto;
import static com.endava.booksharing.utils.mappers.BookMapper.mapBookToBooksResponseDto;
import static com.endava.booksharing.utils.mappers.BookMapper.mapBooksResponseDtoPageToPageableBooksResponseDto;
import static com.endava.booksharing.utils.mappers.BookMapper.updateFromBookRequestDtoToBook;
import static com.endava.booksharing.utils.mappers.BookMapper.setDeletedBookValues;
import static com.endava.booksharing.utils.mappers.BookMapper.setUserAndTagsForBook;
import static com.endava.booksharing.utils.mappers.TagsMapper.mapTagsRequestDtoToTags;

import static com.endava.booksharing.utils.specifications.BookSpec.hasTag;
import static com.endava.booksharing.utils.specifications.BookSpec.filter;
import static com.endava.booksharing.utils.specifications.BookSpec.hasTitle;
import static com.endava.booksharing.utils.specifications.BookSpec.isNotDeleted;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final TagsRepository tagsRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${message.book.not-found-or-deleted}")
    private String messageBookNotFoundOrDeleted;

    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findByIdAndBookStatusIsNot(id, StatusType.DELETED).orElseThrow(() -> {
            log.warn("Book with with id [{}] was not found in the database or is deleted", id);
            return new NotFoundException(format(messageBookNotFoundOrDeleted, id));
        });
        return mapBookToBookResponseDto.apply(book);
    }

    @Transactional
    public Optional<Book> checkIfBookIsNotDeleted(Long id) {
        return bookRepository.findByIdAndBookStatusIsNot(id, StatusType.DELETED);
    }

    @Transactional
    public BookResponseDto saveBook(BookRequestDto bookRequestDto) {
        log.info("Saving book with title [{}]", bookRequestDto.getTitle());
        final List<Tags> tagsFromDatabase = tagsRepository.findAll();

        Book book = mapBookRequestDtoToBook.apply(bookRequestDto);
        setUserAndTagsForBook(userDetailsService.getCurrentUser(), mapTagsRequestDtoToTags(bookRequestDto.getTagList(),
                tagsFromDatabase), book);
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
    public BookResponseDto  updateBook(Long bookId, BookRequestDto bookRequestDto) {
        log.info("Updating the records for book with id [{}]", bookId);

        Book toBeUpdatedBook = bookRepository.findById(bookId).orElseThrow(
                () -> {
                    log.warn("Book with id [{}] is not present in database", bookId);
                    return new NotFoundException("Book with id" + bookId + " was not found in database");
                });
        updateFromBookRequestDtoToBook(toBeUpdatedBook, bookRequestDto);
        toBeUpdatedBook = bookRepository.save(toBeUpdatedBook);

        return mapBookToBookResponseDto.apply(toBeUpdatedBook);
    }

    public PageableBooksResponseDto getBooks(String find, int page, int size, String sort) {
        Page<BooksResponseDto> booksResponseDtoPage = bookRepository.findAll(isNotDeleted().and(hasTitle(find)),
                PageRequest.of(page, size, Sort.by(sort)))
                .map(mapBookToBooksResponseDto);

        return mapBooksResponseDtoPageToPageableBooksResponseDto.apply(booksResponseDtoPage);
    }

    public PageableBooksResponseDto getFilteredBooks(String authorName, String language, Set<String> tags, Set<String> genTags,
                                                     String tagsFind, String status, int page, int size, String sort) {
        List<Book> filterList = new ArrayList<>();
        List<Specification<Book>> specBook = new ArrayList<>();

        StatusType statusT = null;
        if (status != null) {
            if (!status.isEmpty()) {
                statusT = StatusType.valueOf(status);
            }
        }

        specBook.add(isNotDeleted());
        specBook.add(filter(authorName, language, tags, tagsFind, statusT));

        Specification<Book> globalSpecBook = Specification.where(specBook.get(0));
        for (int i = 1; i < specBook.size(); i++) {
            globalSpecBook = globalSpecBook.and(specBook.get(i));
        }

        Specification<Book> genSpec = globalSpecBook;
        if (genTags != null) {
            for (String genTag : genTags) {
                genSpec = genSpec.and(hasTag(genTag));
                filterList.addAll(bookRepository.findAll(genSpec));
                genSpec = globalSpecBook;
            }
        } else {
            filterList = bookRepository.findAll(globalSpecBook);
        }

        List<Book> filterListWithoutDuplicates = filterList.stream().distinct().collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filterListWithoutDuplicates.size());
        Page<BooksResponseDto> booksResponseDtoPage = new PageImpl<>(filterListWithoutDuplicates.subList(start, end), pageable, filterListWithoutDuplicates.size())
                .map(mapBookToBooksResponseDto);

        return mapBooksResponseDtoPageToPageableBooksResponseDto.apply(booksResponseDtoPage);
    }

    public List<Object> getBooksLanguages() {
        return bookRepository.findBookLanguage();
    }
}
