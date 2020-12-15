package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.model.Assignments;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignmentsMapper {

    public static final Function<Assignments, AssignmentsResponseDto> mapAssignmentsToAssignmentsResponseDto =
            assignments -> AssignmentsResponseDto.builder()
                    .id(assignments.getId())
                    .assignDate(assignments.getAssignDate())
                    .dueDate(assignments.getDueDate())
                    .bookName(assignments.getBook().getTitle())
                    .bookId(assignments.getBook().getId())
                    .build();
}