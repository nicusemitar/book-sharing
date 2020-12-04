package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.repository.AssignmentsRepository;
import com.endava.booksharing.utils.AssignmentsTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentsServiceTest {

    @Mock
    private AssignmentsRepository assignmentsRepository;

    @InjectMocks
    private AssignmentsService assignmentsService;

    @Test
    void shouldGetAssignmentsByUserId() {

        final List<AssignmentsResponseDto> expectedResponseDto = Arrays.asList(AssignmentsTestUtils.ASSIGNMENTS_RESPONSE_DTO);
        when(assignmentsRepository.getAssignmentsByUserId(ID_ONE)).thenReturn((AssignmentsTestUtils.assignmentsList));

        final List<AssignmentsResponseDto> actualResponseDto = assignmentsService.getAssignmentsByUserId(ID_ONE);

        assertEquals(expectedResponseDto.get(0).getId(), actualResponseDto.get(0).getId());

        verify(assignmentsRepository).getAssignmentsByUserId(ID_ONE);
    }
}