package com.endava.booksharing.utils.mappers;

import com.endava.booksharing.api.dto.PageableTimeExtendResponseDto;
import com.endava.booksharing.api.dto.TimeExtendRequestDto;
import com.endava.booksharing.api.dto.TimeExtendResponseDto;
import com.endava.booksharing.model.TimeExtend;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TimeExtendMapper {
    public static final Function<TimeExtend, TimeExtendResponseDto> mapTimeExtendToTimeExtendResponseDto =
            timeExtend -> TimeExtendResponseDto.builder()
                    .username(timeExtend.getAssignment().getUser().getUsername())
                    .bookId(timeExtend.getAssignment().getBook().getId())
                    .description(timeExtend.getDescription())
                    .dueDate(timeExtend.getAssignment().getDueDate().toString())
                    .requestedDate(timeExtend.getRequestedDate().toString())
                    .requestId(timeExtend.getId())
                    .build();

    public static final Function<TimeExtendRequestDto, TimeExtend> mapTimeExtendRequestDtoToTimeExtend =
            timeExtendRequestDto -> TimeExtend.builder()
                    .description(timeExtendRequestDto.getDescription())
                    .requestedDate(LocalDate.parse(timeExtendRequestDto.getRequestedDate(),
                            DateTimeFormatter.ofPattern("y-M-d")))
                    .build();

    public static final Function<Page<TimeExtendResponseDto>, PageableTimeExtendResponseDto> mapTimeExtendRequestDtoPageToPageableTimeExtendResponseDto = timeExtendResponseDto -> PageableTimeExtendResponseDto.builder()
            .timeExtendResponseDtoList(timeExtendResponseDto.getContent())
            .totalItems(timeExtendResponseDto.getTotalElements())
            .currentPage(timeExtendResponseDto.getNumber())
            .totalPages(timeExtendResponseDto.getTotalPages())
            .build();
}
