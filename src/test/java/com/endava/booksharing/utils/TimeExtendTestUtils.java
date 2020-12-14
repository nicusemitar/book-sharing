package com.endava.booksharing.utils;

import com.endava.booksharing.api.dto.BooksResponseDto;
import com.endava.booksharing.api.dto.PageableBooksResponseDto;
import com.endava.booksharing.api.dto.PageableTimeExtendResponseDto;
import com.endava.booksharing.api.dto.TimeExtendRequestDto;
import com.endava.booksharing.api.dto.TimeExtendResponseDto;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.TimeExtend;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import static com.endava.booksharing.utils.BooksServiceTestUtils.PAGEABLE;

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
            .requestId(ID_ONE)
            .build();

    public static final List<TimeExtendResponseDto> TIME_EXTEND_RESPONSE_DTO_LIST =
            Collections.singletonList(TIME_EXTEND_RESPONSE_DTO);
    public static final List<TimeExtend> TIME_EXTEND_LIST = Collections.singletonList(TIME_EXTEND_ONE);
    public static final PageImpl<TimeExtend> TIME_EXTEND_PAGE = new PageImpl<>(TIME_EXTEND_LIST, PAGEABLE, 2L);
    public static final PageImpl<TimeExtendResponseDto> TIME_EXTEND_RESPONSE_DTOS = new PageImpl<>(TIME_EXTEND_RESPONSE_DTO_LIST, PAGEABLE, 2L);

    public static final PageableTimeExtendResponseDto PAGEABLE_TIME_EXTEND_RESPONSE_DTO = new PageableTimeExtendResponseDto().toBuilder()
            .totalPages(1)
            .totalItems(1)
            .currentPage(0)
            .timeExtendResponseDtoList(Collections.singletonList(TIME_EXTEND_RESPONSE_DTO))
            .build();
}