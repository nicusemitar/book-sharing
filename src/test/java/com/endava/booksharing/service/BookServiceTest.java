package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.BookResponseDto;
import com.endava.booksharing.api.dto.PageableBooksResponseDto;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.enums.StatusType;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.repository.TagsRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static com.endava.booksharing.TestConstants.BOOK_TITLE_ONE;
import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.ID_TWO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_NOT_DELETED;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_NOT_DELETED_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_ONE_UPDATED;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_PAGE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_REQUEST_DTO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_TWO;
import static com.endava.booksharing.utils.BookTestUtils.DELETED_BOOK;
import static com.endava.booksharing.utils.BookTestUtils.DELETED_BOOK_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.DELETE_BOOK_REQUEST_DTO;
import static com.endava.booksharing.utils.BookTestUtils.PAGEABLE_BOOKS_RESPONSE_DTO;
import static com.endava.booksharing.utils.BookTestUtils.TO_UPDATE_BOOK_REQUEST_DTO;
import static com.endava.booksharing.utils.BookTestUtils.UPDATED_BOOK_RESPONSE_DTO;
import static com.endava.booksharing.utils.TagsTestUtils.DEFAULT_TAG;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private TagsRepository tagsRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(bookService, "messageBookNotFoundOrDeleted",
                "Book with id %s was not found in the database or is deleted");
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(bookRepository, tagsRepository);
    }

    @Test
    public void shouldReturnBookThatIsNotDeleted() {
        when(bookRepository.findByIdAndBookStatusIsNot(ID_TWO, StatusType.DELETED))
                .thenReturn(Optional.of(BOOK_NOT_DELETED));

        assertEquals(bookService.getBook(ID_TWO), BOOK_NOT_DELETED_RESPONSE_DTO);

        verify(bookRepository).findByIdAndBookStatusIsNot(ID_TWO, StatusType.DELETED);
    }

    @Test
    public void shouldNotReturnBook() {
        when(bookRepository.findByIdAndBookStatusIsNot(ID_ONE, StatusType.DELETED))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBook(ID_ONE));

        verify(bookRepository).findByIdAndBookStatusIsNot(ID_ONE, StatusType.DELETED);
    }

    @Test
    void shouldDeleteBook() {
        final BookResponseDto expectedResponseDto = DELETED_BOOK_RESPONSE_DTO;

        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(bookRepository.save(any(Book.class))).thenReturn(DELETED_BOOK);

        final BookResponseDto actualResponseDto = bookService.deleteBook(DELETE_BOOK_REQUEST_DTO);

        assertAll(
                () -> assertEquals(expectedResponseDto.getTitle(), actualResponseDto.getTitle()),
                () -> assertEquals(expectedResponseDto.getAuthor(), actualResponseDto.getAuthor()),
                () -> assertEquals(expectedResponseDto.getDeletedDate(), actualResponseDto.getDeletedDate()),
                () -> assertEquals(expectedResponseDto.getDeletedWhy(), actualResponseDto.getDeletedWhy()),
                () -> assertEquals(expectedResponseDto.getDeletedBy(), actualResponseDto.getDeletedBy()),
                () -> assertEquals(expectedResponseDto.getStatus(), actualResponseDto.getStatus())
        );

        verify(userDetailsService).getCurrentUser();
        verify(bookRepository).findById(ID_ONE);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void shouldThrowBookNotFoundOnDelete() {
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookService.deleteBook(DELETE_BOOK_REQUEST_DTO));
    }

    @Test
    void shouldUpdateBook() {
        final BookResponseDto expectedResponseDto = UPDATED_BOOK_RESPONSE_DTO;

        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(bookRepository.save(any(Book.class))).thenReturn(BOOK_ONE_UPDATED);

        final BookResponseDto actualResponseDto = bookService.updateBook(ID_ONE, TO_UPDATE_BOOK_REQUEST_DTO);

        assertAll(
                () -> assertEquals(expectedResponseDto.getTitle(), actualResponseDto.getTitle()),
                () -> assertEquals(expectedResponseDto.getAuthor(), actualResponseDto.getAuthor())
        );

        verify(bookRepository).findById(ID_ONE);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void shouldThrowBookNotFoundOnUpdate() {
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.updateBook(ID_ONE, BOOK_REQUEST_DTO));
    }

    @Test
    void shouldSaveBook() {
        final BookResponseDto expectedResponseDto = BOOK_RESPONSE_DTO;

        when(tagsRepository.findAll()).thenReturn(Collections.singletonList(DEFAULT_TAG));
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(bookRepository.save(any(Book.class))).thenReturn(BOOK_TWO);

        final BookResponseDto actualResponseDto = bookService.saveBook(BOOK_REQUEST_DTO);
        assertAll(
                () -> assertEquals(expectedResponseDto.getAuthor(), actualResponseDto.getAuthor()),
                () -> assertEquals(expectedResponseDto.getTitle(), actualResponseDto.getTitle()),
                () -> assertEquals(expectedResponseDto.getAddedBy(), actualResponseDto.getAddedBy())
        );

        verify(tagsRepository).findAll();
        verify(userDetailsService).getCurrentUser();
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void shouldReturnAllPageableBooksResponseDtoByTitle() {
        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(BOOK_PAGE);

        PageableBooksResponseDto expectedPageableBooksResponseDto = PAGEABLE_BOOKS_RESPONSE_DTO;
        PageableBooksResponseDto actualPageableBooksResponseDto = bookService.getBooks(BOOK_TITLE_ONE, 0, 15, "title");

        assertEquals(expectedPageableBooksResponseDto, actualPageableBooksResponseDto);
        verify(bookRepository).findAll(any(Specification.class), any(Pageable.class));
    }
}