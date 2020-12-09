package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.repository.AssignmentsRepository;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.UserAlreadyHaveAssignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.USER_ID;
import static com.endava.booksharing.utils.AssignmentsTestUtils.ASSIGNMENTS_RESPONSE_DTO;
import static com.endava.booksharing.utils.AssignmentsTestUtils.ASSIGNMENTS_RESPONSE_DTO_TWO;
import static com.endava.booksharing.utils.AssignmentsTestUtils.AssignmentsForStatusTest;
import static com.endava.booksharing.utils.AssignmentsTestUtils.OneAssignments;
import static com.endava.booksharing.utils.AssignmentsTestUtils.listOfAssignmentsForUser;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_TWO_BUSY;
import static com.endava.booksharing.utils.BooksServiceTestUtils.BOOK_ONE;
import static com.endava.booksharing.utils.BooksServiceTestUtils.BOOK_ONE_BUSY;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.mappers.AssignmentsMapper.mapAssignmentsToAssignmentsResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentsServiceTest {

    @Mock
    private AssignmentsRepository assignmentsRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AssignmentsService assignmentsService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(assignmentsService, "messageBookNotFound",
                "messageBookNotFound");
        ReflectionTestUtils.setField(assignmentsService, "assignmentNotFound",
                "assignmentNotFound");
        ReflectionTestUtils.setField(assignmentsService, "userAlreadyHaveAssignment",
                "userAlreadyHaveAssignment");
        ReflectionTestUtils.setField(assignmentsService,"getUserHaveToManyWaitingAssignments","getUserHaveToManyWaitingAssignments");
    }

    @Test
    void shouldGetAssignmentsByUserId() {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsRepository.getAssignmentsByUserId(ID_ONE)).thenReturn(OneAssignments());

        final List<AssignmentsResponseDto> expectedResponseDto = OneAssignments().stream().map(mapAssignmentsToAssignmentsResponseDto).collect(Collectors.toList());
        final List<AssignmentsResponseDto> actualResponseDto = assignmentsService.getAssignmentsByUser();

        assertEquals(expectedResponseDto.get(0).getId(), actualResponseDto.get(0).getId());

        verify(assignmentsRepository).getAssignmentsByUserId(ID_ONE);
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldSaveNewAssignment() {
        AssignmentsResponseDto expectedAssign = ASSIGNMENTS_RESPONSE_DTO;

        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(assignmentsRepository.getAssignmentsByUserId(USER_ID)).thenReturn(Collections.emptyList());
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);

        AssignmentsResponseDto returnedAssign = assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE.getId());

        assertEquals(expectedAssign.getBookName(), returnedAssign.getBookName());

        verify(assignmentsRepository).save(any());
    }

    @Test
    void shouldReturnBookNotFound() {
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE.getId()));
    }

    @Test
    void shouldReturnExceptionUserHaveToManyWaitingAssignments() {
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsRepository.getAssignmentsByUserId(any()))
                .thenReturn(listOfAssignmentsForUser());

        assertThrows(UserAlreadyHaveAssignment.class, () -> assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE.getId()));
    }

    @Test
    void shouldAddToWaitingList() {
        AssignmentsResponseDto expectedAssign = ASSIGNMENTS_RESPONSE_DTO;

        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE_BUSY));
        when(assignmentsRepository.getAssignmentsByUserId(USER_ID)).thenReturn(Collections.emptyList());
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);

        AssignmentsResponseDto returnedAssign = assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE_BUSY.getId());

        assertEquals(expectedAssign.getBookName(), returnedAssign.getBookName());

        verify(assignmentsRepository).save(any());
    }


    @Test
    void shouldReturnExceptionAssignment() {
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_TWO_BUSY));
        when(assignmentsRepository.getAssignmentsByUserId(USER_ID)).thenReturn(listOfAssignmentsForUser());
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);

        assertThrows(UserAlreadyHaveAssignment.class, () -> assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE.getId()));
    }

    @Test
    void shouldReturnNotFoundException() {
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsRepository.getAllByBookId(ID_ONE)).thenReturn(Collections.emptyList());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BOOK_TWO_BUSY));
        assertThrows(NotFoundException.class, () -> assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE.getId()));
    }


    @Test
    void shouldSaveWaitingAssignment() {
        AssignmentsResponseDto expectedAssign = ASSIGNMENTS_RESPONSE_DTO_TWO;

        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(assignmentsRepository.getAllByBookId(ID_ONE)).thenReturn(OneAssignments());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BOOK_TWO_BUSY));

        AssignmentsResponseDto returnedAssign = assignmentsService.saveAssigmentForCurrentUserForBookWithID(BOOK_ONE.getId());

        assertEquals(expectedAssign.getBookName(), returnedAssign.getBookName());

        verify(assignmentsRepository).save(any());
    }

    @Test
    public void shouldChangeTheStatusOfBook(){
        when(assignmentsRepository.getAllActiveAndWaitingAssignments()).thenReturn(AssignmentsForStatusTest());
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(BOOK_ONE));

        assignmentsService.verifyBookStatus();

        verify(bookRepository).saveAll(any());
    }
}