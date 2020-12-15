package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.api.dto.PageableTimeExtendResponseDto;
import com.endava.booksharing.api.dto.TimeExtendRequestDto;
import com.endava.booksharing.api.dto.TimeExtendResponseDto;
import com.endava.booksharing.model.Assignments;
import com.endava.booksharing.model.TimeExtend;
import com.endava.booksharing.repository.AssignmentsRepository;
import com.endava.booksharing.repository.TimeExtendRepository;
import com.endava.booksharing.utils.exceptions.AccessDeniedException;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.RequestedDateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.endava.booksharing.utils.mappers.TimeExtendMapper.mapTimeExtendRequestDtoPageToPageableTimeExtendResponseDto;
import static com.endava.booksharing.utils.mappers.AssignmentsMapper.mapAssignmentsToAssignmentsResponseDto;
import static com.endava.booksharing.utils.mappers.TimeExtendMapper.mapTimeExtendRequestDtoToTimeExtend;
import static com.endava.booksharing.utils.mappers.TimeExtendMapper.mapTimeExtendToTimeExtendResponseDto;
import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeExtendService {

    private final AssignmentsRepository assignmentsRepository;
    private final TimeExtendRepository timeExtendRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${message.assignment.not-found}")
    private String messageAssignmentNotFound;

    @Value("${message.request.not-found}")
    private String timeExtendRequestNotFound;

    public TimeExtendResponseDto save(TimeExtendRequestDto timeExtendRequestDto, Long assignmentId) {
        log.info("Saving request extend time for assigment with: id [{}]", assignmentId);
        Assignments assignments = assignmentsRepository.findById(assignmentId).orElseThrow(
                () -> {
                    log.warn("Assignment with id [{}] was not found in the database", assignmentId);
                    return new NotFoundException(format(messageAssignmentNotFound, assignmentId));
                }
        );
        checkIfRequestedDateIsGreaterThanAssignmentDueDate(LocalDate.parse(timeExtendRequestDto.getRequestedDate(),
                DateTimeFormatter.ofPattern("y-M-d")), assignments.getDueDate(), assignments.getAssignDate());
        if (assignments.getUser().getUsername().equals(userDetailsService.getCurrentUser().getUsername())) {
            Optional<TimeExtend> optional = timeExtendRepository.findByAssignmentId(assignmentId);
            TimeExtend timeExtend = mapTimeExtendRequestDtoToTimeExtend.apply(timeExtendRequestDto);
            if (optional.isPresent()) {
                timeExtend.setId(optional.get().getId());
            }
            timeExtend.setAssignment(assignments);
            timeExtend = timeExtendRepository.save(timeExtend);
            return mapTimeExtendToTimeExtendResponseDto.apply(timeExtend);
        } else {
            log.warn("User with username [{}] doesn't have permission to request extend time for assignment with id [{}]",
                    userDetailsService.getCurrentUser().getUsername(), assignmentId);
            throw new AccessDeniedException("You don't have permission to request extend time for this assignment!");
        }
    }

    public PageableTimeExtendResponseDto getAllRequests(int page, int size) {
        log.info("Getting all time extend requests from database");

        Page<TimeExtendResponseDto> timeExtendResponseDtoPage = timeExtendRepository.findAll(
                PageRequest.of(page, size))
                .map(mapTimeExtendToTimeExtendResponseDto);
        return mapTimeExtendRequestDtoPageToPageableTimeExtendResponseDto.apply(timeExtendResponseDtoPage);
    }

    public void deleteRequest(Long id) {
        log.info("Deleting the request with id [{}]", id);

        timeExtendRepository.deleteById(id);
    }

    public void checkIfRequestedDateIsGreaterThanAssignmentDueDate(LocalDate requestedDate, LocalDate dueDate,
                                                                   LocalDate assignDate) {
        if (dueDate.isAfter(requestedDate)) {
            throw new RequestedDateException("Requested date is smaller than current due date!");
        }
        if (dueDate.isEqual(requestedDate)) {
            throw new RequestedDateException("Requested date is the same as current due date!");
        }
        if (requestedDate.isAfter(assignDate.plusDays(29))) {
            throw new RequestedDateException("Requested date is greater than 30 days since assigned date!");
        }
    }

    @Transactional
    public List<AssignmentsResponseDto> acceptRequest(Long id, TimeExtendRequestDto timeExtendRequestDto) {
        TimeExtend timeExtend = timeExtendRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Time extend request with id [{}] was not found in the database", id);
                    return new NotFoundException(format(timeExtendRequestNotFound, id));
                }
        );
        Assignments assignments = assignmentsRepository.findById(timeExtend.getAssignment().getId()).orElseThrow(
                () -> {
                    log.warn("Assignment with id [{}] was not found in the database", timeExtend.getAssignment().getId());
                    return new NotFoundException(format(messageAssignmentNotFound, timeExtend.getAssignment().getId()));
                }
        );
        log.info("Updating the due date for the assignment with id [{}]",assignments.getId());

        List<Assignments> activeAndWaitingAssignments = assignmentsRepository.getActiveAndWaitingAssignmentsByBookId(assignments.getBook().getId());
        int difference = LocalDate.parse(timeExtendRequestDto.getRequestedDate()).compareTo(assignments.getDueDate());
        assignments.setDueDate(assignments.getDueDate().plusDays(difference));
        activeAndWaitingAssignments.stream().filter(assignments1 -> !assignments1.getId().equals(assignments.getId()))
                .forEach(assignments1 ->
                        {
                            assignments1.setAssignDate(assignments1.getAssignDate().plusDays(difference));
                            assignments1.setDueDate(assignments1.getDueDate().plusDays(difference));
                        }
                );
        List<AssignmentsResponseDto> assignmentsResponseDtos = assignmentsRepository.saveAll(activeAndWaitingAssignments)
                .stream().map(mapAssignmentsToAssignmentsResponseDto).collect(Collectors.toList());
        timeExtendRepository.deleteById(id);

        return assignmentsResponseDtos;
    }

}