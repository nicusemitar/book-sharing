package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.model.Assignments;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.endava.booksharing.TestConstants.BOOK_TITLE;
import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.ASSIGN_DATE;
import static com.endava.booksharing.TestConstants.DUE_DATE;
import static com.endava.booksharing.TestConstants.BOOK_TITLE_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_ONE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_THREE;
import static com.endava.booksharing.utils.BookTestUtils.BOOK_TWO;
import static com.endava.booksharing.utils.UserTestUtils.USER_ONE;
import static com.endava.booksharing.utils.UserTestUtils.USER_TWO;

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

    public static final AssignmentsResponseDto ASSIGNMENTS_RESPONSE_DTO_TWO = AssignmentsResponseDto
            .builder()
            .id(ID_ONE)
            .assignDate(ASSIGN_DATE)
            .dueDate(DUE_DATE)
            .bookName(BOOK_TITLE)
            .build();

    public static List<Assignments> OneAssignments(){
        List<Assignments> assignments = new ArrayList<>();



        assignments.add(
                Assignments.builder()
                        .id(2l)
                        .book(BOOK_ONE)
                        .user(USER_TWO)
                        .assignDate(LocalDate.now())
                        .dueDate(LocalDate.now().plus(2, ChronoUnit.WEEKS))
                        .build()
        );
        return assignments;
    }
    public static List<Assignments> listOfAssignmentsForUser(){
        List<Assignments> assignments = new ArrayList<>();

        assignments.add(
                Assignments.builder()
                        .id(1l)
                        .book(BOOK_ONE)
                        .user(USER_ONE)
                        .assignDate(LocalDate.now())
                        .dueDate(LocalDate.now().plus(2,ChronoUnit.WEEKS))
                        .build()
        );
        assignments.add(
                Assignments.builder()
                        .id(2l)
                        .book(BOOK_TWO)
                        .user(USER_ONE)
                        .assignDate(LocalDate.now())
                        .dueDate(LocalDate.now().plus(2,ChronoUnit.WEEKS))
                        .build()
        );
        assignments.add(
                Assignments.builder()
                        .id(3l)
                        .book(BOOK_THREE)
                        .user(USER_ONE)
                        .assignDate(LocalDate.now())
                        .dueDate(LocalDate.now().plus(2,ChronoUnit.WEEKS))
                        .build()
        );

        return assignments;
    }

    public static List<Assignments> AssignmentsForStatusTest() {
        List<Assignments> assignments = new ArrayList<>();


        assignments.add(
                Assignments.builder()
                        .id(1l)
                        .book(BOOK_ONE)
                        .user(USER_TWO)
                        .assignDate(LocalDate.parse("2019-12-05"))
                        .dueDate(LocalDate.parse("2019-12-05").plus(2, ChronoUnit.WEEKS))
                        .build()
        );

        return assignments;
    }
}