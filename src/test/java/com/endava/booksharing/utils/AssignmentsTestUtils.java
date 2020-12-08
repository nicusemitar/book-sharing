package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.model.Assignments;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.ASSIGN_DATE;
import static com.endava.booksharing.TestConstants.DUE_DATE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignmentsTestUtils {

    public static final AssignmentsResponseDto ASSIGNMENTS_RESPONSE_DTO = AssignmentsResponseDto
            .builder()
            .id(ID_ONE)
            .assignDate(ASSIGN_DATE)
            .dueDate(DUE_DATE)
            .bookName(BOOK_TITLE_ONE)
            .build();

    public static final Assignments ASSIGNMENTS_ONE = Assignments
            .builder()
            .id(ID_ONE)
            .assignDate(ASSIGN_DATE)
            .dueDate(DUE_DATE)
            .book(BOOK_ONE)
            .build();

    public static final Assignments ASSIGNMENTS_TWO = Assignments
            .builder()
            .id(ID_ONE)
            .assignDate(ASSIGN_DATE)
            .dueDate(DUE_DATE)
            .book(BOOK_ONE)
            .user(USER_ONE)
            .build();

    public static List<Assignments> assignmentsList = Collections.singletonList(ASSIGNMENTS_ONE);
}