package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.repository.AssignmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.endava.booksharing.utils.mappers.AssignmentsMapper.mapAssignmentsToAssignmentsResponseDto;

@Service
@RequiredArgsConstructor
public class AssignmentsService {

    private final AssignmentsRepository assignmentsRepository;

    public List<AssignmentsResponseDto> getAssignmentsByUserId(final Long id) {
        return assignmentsRepository.getAssignmentsByUserId(id)
                .stream().map(mapAssignmentsToAssignmentsResponseDto).collect(Collectors.toList());
    }
}