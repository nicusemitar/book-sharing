package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.TimeExtendRequestDto;
import com.endava.booksharing.api.dto.TimeExtendResponseDto;
import com.endava.booksharing.model.TimeExtend;

import static com.endava.booksharing.TestConstants.ID_ONE;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DESCRIPTION;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DATE_VALID;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DATE_VALID_REQUEST_AS_STRING;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DATE_VALID_AS_STRING;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DATE_SMALLER_THAN_DUE_DATE_AS_STRING;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DATE_EQUAL_WITH_DUE_DATE_AS_STRING;
import static com.endava.booksharing.TestConstants.TIME_EXTEND_DATE_GREATER_THAN_ASSIGN_DATE_PLUS_30_DAYS_AS_STRING;
import static com.endava.booksharing.TestConstants.USER_ONE_USERNAME;
import static com.endava.booksharing.TestConstants.DUE_DATE_AS_STRING;
import static com.endava.booksharing.utils.AssignmentsTestUtils.ASSIGNMENTS_TWO;

public class TimeExtendTestUtils {
    public static final TimeExtend TIME_EXTEND_ONE = TimeExtend.builder()
            .id(ID_ONE)
            .description(TIME_EXTEND_DESCRIPTION)
            .assignment(ASSIGNMENTS_TWO)
            .requestedDate(TIME_EXTEND_DATE_VALID)
            .build();

    public static final TimeExtend TIME_EXTEND_ONE_NO_ID = TimeExtend.builder()
            .description(TIME_EXTEND_DESCRIPTION)
            .assignment(ASSIGNMENTS_TWO)
            .requestedDate(TIME_EXTEND_DATE_VALID)
            .build();

    public static final TimeExtendRequestDto TIME_EXTEND_REQUEST_DTO = TimeExtendRequestDto.builder()
            .description(TIME_EXTEND_DESCRIPTION)
            .requestedDate(TIME_EXTEND_DATE_VALID_REQUEST_AS_STRING)
            .build();

    public static final TimeExtendRequestDto TIME_EXTEND_REQUEST_DTO_DATE_EQUAL_WITH_DUE_DATE =
            TimeExtendRequestDto.builder()
                    .description(TIME_EXTEND_DESCRIPTION)
                    .requestedDate(TIME_EXTEND_DATE_EQUAL_WITH_DUE_DATE_AS_STRING)
                    .build();

    public static final TimeExtendRequestDto TIME_EXTEND_REQUEST_DTO_DATE_SMALLER_THAN_DUE_DATE =
            TimeExtendRequestDto.builder()
                    .description(TIME_EXTEND_DESCRIPTION)
                    .requestedDate(TIME_EXTEND_DATE_SMALLER_THAN_DUE_DATE_AS_STRING)
                    .build();

    public static final TimeExtendRequestDto TIME_EXTEND_REQUEST_DTO_DATE_GREATER_THAN_ASSIGN_DATE_30_DAYS =
            TimeExtendRequestDto.builder()
                    .description(TIME_EXTEND_DESCRIPTION)
                    .requestedDate(TIME_EXTEND_DATE_GREATER_THAN_ASSIGN_DATE_PLUS_30_DAYS_AS_STRING)
                    .build();

    public static final TimeExtendResponseDto TIME_EXTEND_RESPONSE_DTO = TimeExtendResponseDto.builder()
            .bookId(ID_ONE)
            .username(USER_ONE_USERNAME)
            .description(TIME_EXTEND_DESCRIPTION)
            .dueDate(DUE_DATE_AS_STRING)
            .requestedDate(TIME_EXTEND_DATE_VALID_AS_STRING)
            .build();
}