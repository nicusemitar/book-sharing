package com.endava.booksharing.service;

import com.endava.booksharing.repository.AssignmentsRepository;
import com.endava.booksharing.repository.TimeExtendRepository;
import com.endava.booksharing.utils.exceptions.AccessDeniedException;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.RequestedDateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.utils.AssignmentsTestUtils.ASSIGNMENTS_TWO;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_ONE;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_ONE_NO_ID;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_REQUEST_DTO;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_REQUEST_DTO_DATE_EQUAL_WITH_DUE_DATE;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_REQUEST_DTO_DATE_SMALLER_THAN_DUE_DATE;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_REQUEST_DTO_DATE_GREATER_THAN_ASSIGN_DATE_30_DAYS;
import static com.endava.booksharing.utils.TimeExtendTestUtils.TIME_EXTEND_RESPONSE_DTO;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_TWO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TimeExtendServiceTest {

    @Mock
    private AssignmentsRepository assignmentsRepository;

    @Mock
    private TimeExtendRepository timeExtendRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private TimeExtendService timeExtendService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(timeExtendService, "messageAssignmentNotFound",
                "Assignment with id %s was not found in the database");
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(assignmentsRepository, timeExtendRepository);
    }

    @Test
    public void shouldSaveTimeExtend() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(timeExtendRepository.save(TIME_EXTEND_ONE_NO_ID)).thenReturn(TIME_EXTEND_ONE);

        assertEquals(timeExtendService.save(TIME_EXTEND_REQUEST_DTO, ID_ONE), TIME_EXTEND_RESPONSE_DTO);

        verify(assignmentsRepository).findById(ID_ONE);
        verify(userDetailsService).getCurrentUser();
        verify(timeExtendRepository).findByAssignmentId(ID_ONE);
        verify(timeExtendRepository).save(TIME_EXTEND_ONE_NO_ID);
    }

    @Test
    public void shouldUpdateTimeExtend() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));
        when(userDetailsService.getCurrentUser()).thenReturn(USER_ONE);
        when(timeExtendRepository.findByAssignmentId(ID_ONE)).thenReturn(Optional.of(TIME_EXTEND_ONE));
        when(timeExtendRepository.save(TIME_EXTEND_ONE)).thenReturn(TIME_EXTEND_ONE);

        assertEquals(timeExtendService.save(TIME_EXTEND_REQUEST_DTO, ID_ONE), TIME_EXTEND_RESPONSE_DTO);

        verify(assignmentsRepository).findById(ID_ONE);
        verify(userDetailsService).getCurrentUser();
        verify(timeExtendRepository).findByAssignmentId(ID_ONE);
        verify(timeExtendRepository).save(TIME_EXTEND_ONE);
    }

    @Test
    public void shouldThrowAssignmentNotFoundException() {
        assertThrows(NotFoundException.class, () -> timeExtendService.save(TIME_EXTEND_REQUEST_DTO, ID_ONE));

        verify(assignmentsRepository).findById(ID_ONE);
    }

    @Test
    public void shouldThrowAccessDenied() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));
        when(userDetailsService.getCurrentUser()).thenReturn(USER_TWO);

        assertThrows(AccessDeniedException.class, () -> timeExtendService.save(TIME_EXTEND_REQUEST_DTO, ID_ONE));

        verify(assignmentsRepository).findById(ID_ONE);
        verify(userDetailsService, times(2)).getCurrentUser();
    }

    @Test
    public void shouldThrowUserNotFoundException() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));
        when(userDetailsService.getCurrentUser()).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> timeExtendService.save(TIME_EXTEND_REQUEST_DTO, ID_ONE));

        verify(assignmentsRepository).findById(ID_ONE);
        verify(userDetailsService).getCurrentUser();
    }

    @Test
    public void shouldThrowRequestedDateExceptionSmallerDate() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));

        assertThrows(RequestedDateException.class, () ->
                timeExtendService.save(TIME_EXTEND_REQUEST_DTO_DATE_SMALLER_THAN_DUE_DATE, ID_ONE));

        verify(assignmentsRepository).findById(ID_ONE);
    }

    @Test
    public void shouldThrowRequestedDateExceptionEqualsDate() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));

        assertThrows(RequestedDateException.class, () ->
                timeExtendService.save(TIME_EXTEND_REQUEST_DTO_DATE_EQUAL_WITH_DUE_DATE, ID_ONE));

        verify(assignmentsRepository).findById(ID_ONE);
    }

    @Test
    public void shouldThrowRequestedDateExceptionGreaterDate() {
        when(assignmentsRepository.findById(ID_ONE)).thenReturn(Optional.of(ASSIGNMENTS_TWO));

        assertThrows(RequestedDateException.class, () ->
                timeExtendService.save(TIME_EXTEND_REQUEST_DTO_DATE_GREATER_THAN_ASSIGN_DATE_30_DAYS, ID_ONE));

        verify(assignmentsRepository).findById(ID_ONE);
    }
}
