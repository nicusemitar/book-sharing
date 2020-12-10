package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.ReviewResponseDto;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.repository.ReviewRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_DELETED;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_ONE;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_TWO;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_RESPONSE_DTO_ONE;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_RESPONSE_DTO_TWO;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_REQUEST_DTO;
import static com.endava.booksharing.utils.ReviewTestUtils.REVIEW_ONE_NO_ID;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(reviewService, "messageBookNotFound",
                "Book with id %s was not found in the database");
    }

    @AfterEach
    void tearDown(){
        verifyNoMoreInteractions(bookRepository, reviewRepository);
    }

    @Test
    public void shouldReturnReviews() {
        when(reviewRepository.getAllReviewsByBookIdDesc(ID_ONE)).thenReturn(Arrays.asList(REVIEW_ONE, REVIEW_TWO));

        final List<ReviewResponseDto> reviewResponseDtos = reviewService.getAllReviews(ID_ONE);

        assertTrue(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_ONE));
        assertTrue(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_TWO));

        verify(reviewRepository).getAllReviewsByBookIdDesc(ID_ONE);
    }

    @Test
    public void shouldNotReturnReviews() {
        final List<ReviewResponseDto> reviewResponseDtos = reviewService.getAllReviews(ID_ONE);

        assertFalse(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_ONE));
        assertFalse(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_TWO));

        verify(reviewRepository).getAllReviewsByBookIdDesc(ID_ONE);
    }

    @Test
    public void shouldSaveReview() {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_DELETED));
        when(reviewRepository.save(REVIEW_ONE_NO_ID)).thenReturn(REVIEW_ONE);

        assertEquals(reviewService.save(REVIEW_REQUEST_DTO, ID_ONE), REVIEW_RESPONSE_DTO_ONE);

        verify(userDetailsService).getCurrentUser();
        verify(bookRepository).findById(ID_ONE);
        verify(reviewRepository).save(REVIEW_ONE_NO_ID);
    }

    @Test
    public void shouldNotSaveReviewNoBookFound() {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);

        assertThrows(NotFoundException.class, () -> reviewService.save(REVIEW_REQUEST_DTO, ID_ONE));

        verify(userDetailsService).getCurrentUser();
        verify(bookRepository).findById(ID_ONE);
    }

    @Test
    public void shouldNotSaveReviewNoUserFound() {
        when(userDetailsService.getCurrentUser()).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> reviewService.save(REVIEW_REQUEST_DTO, ID_ONE));

        verify(userDetailsService).getCurrentUser();
    }
}
