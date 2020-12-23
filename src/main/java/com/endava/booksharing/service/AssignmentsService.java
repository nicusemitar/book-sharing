package com.endava.booksharing.service;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.model.Assignments;
import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.User;
import com.endava.booksharing.model.enums.StatusType;
import com.endava.booksharing.repository.AssignmentsRepository;
import com.endava.booksharing.repository.BookRepository;
import com.endava.booksharing.utils.exceptions.NotFoundException;
import com.endava.booksharing.utils.exceptions.UserAlreadyHaveAssignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.endava.booksharing.utils.mappers.AssignmentsMapper.mapAssignmentsToAssignmentsResponseDto;
import static java.lang.String.format;
import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentsService {

    private final AssignmentsRepository assignmentsRepository;
    private final BookRepository bookRepository;
    private final UserDetailsServiceImpl userDetailsService;


    @Value("${message.book.not-found}")
    private String messageBookNotFound;
    @Value("${message.assignment.not-found}")
    private String assignmentNotFound;
    @Value("${message.user-already-have-assignment}")
    private String userAlreadyHaveAssignment;
    @Value("${message.user-have-to-many-waiting-assignments}")
    private String getUserHaveToManyWaitingAssignments;

    public List<AssignmentsResponseDto> getAssignmentsByUser() {
        return getActiveAndWaitingAssignments().
                stream().
                map(mapAssignmentsToAssignmentsResponseDto).
                collect(Collectors.toList());
    }

    @Transactional
    public AssignmentsResponseDto saveAssigmentForCurrentUserForBookWithID(Long bookID) {
        LocalDate nowDate = now();
        User currentUser = userDetailsService.getCurrentUser();

        Book assignBook = bookRepository.findById(bookID).orElseThrow(() -> {
            log.warn("Book with with id [{}] was not found in database", bookID);
            return new NotFoundException(format(messageBookNotFound, bookID));
        });

        List<Assignments> activeAndWaitingAssignmentsForThisUser = getActiveAndWaitingAssignments();
        List<Assignments> allAssignmentsForThisBook = assignmentsRepository.getAllByBookId(bookID);

        if (activeAndWaitingAssignmentsForThisUser.stream().count() >= 3) {
            throw new UserAlreadyHaveAssignment(format(getUserHaveToManyWaitingAssignments, currentUser.getUsername()));
        }

        if (assignBook.getBookStatus().equals(StatusType.BUSY)) {
            if (activeAndWaitingAssignmentsForThisUser.stream().anyMatch(assignments -> assignments.getBook().getId().equals(bookID))) {
                throw new UserAlreadyHaveAssignment(format(userAlreadyHaveAssignment, currentUser.getUsername()));
            }
            Assignments lastAssignForThisBook = allAssignmentsForThisBook.stream().max(Assignments::compareTo)
                    .orElseThrow(() -> {
                        log.warn("Last assigment for book with id:[{}] Not Found", bookID);
                        return new NotFoundException(format(assignmentNotFound, bookID));
                    });
            Assignments saveAssignment = Assignments.builder()
                    .assignDate(lastAssignForThisBook.getDueDate().plus(1, ChronoUnit.DAYS))
                    .dueDate(lastAssignForThisBook.getDueDate().plus(2, ChronoUnit.WEEKS).plus(1, ChronoUnit.DAYS))
                    .book(assignBook)
                    .user(currentUser)
                    .build();
            assignmentsRepository.save(saveAssignment);
            log.info("Assigment for user :" + currentUser.getUsername() + "added to wait list");

            return mapAssignmentsToAssignmentsResponseDto.apply(saveAssignment);
        } else {
            assignBook.setBookStatus(StatusType.BUSY);
            Assignments saveAssignment = Assignments.builder()
                    .user(currentUser)
                    .assignDate(nowDate)
                    .book(assignBook)
                    .dueDate(nowDate.plus(2, ChronoUnit.WEEKS))
                    .build();
            bookRepository.save(assignBook);
            assignmentsRepository.save(saveAssignment);
            log.info("Assigment for user :" + currentUser.getUsername() + "created");
            return mapAssignmentsToAssignmentsResponseDto.apply(saveAssignment);
        }
    }

    private List<Assignments> getActiveAssignmentsForCurrentUser() {
        User currentUser = userDetailsService.getCurrentUser();
        LocalDate nowDate = now();

        return assignmentsRepository.getAssignmentsByUserId(currentUser.getId())
                .stream().filter(assignment ->
                        (assignment.getAssignDate().isBefore(now()) || assignment.getAssignDate().isEqual(nowDate)) &&
                                (assignment.getDueDate().isAfter(nowDate) || assignment.getDueDate().isEqual(nowDate))
                ).collect(Collectors.toList());
    }

    private List<Assignments> getActiveAndWaitingAssignments() {
        User currentUser = userDetailsService.getCurrentUser();
        LocalDate nowDate = now();

        return assignmentsRepository.getAssignmentsByUserId(currentUser.getId())
                .stream().filter(
                        assignments -> (assignments.getAssignDate().isAfter(nowDate)) || (assignments.getAssignDate().isEqual(nowDate))
                ).collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(cron = "*/10 * * * * *")//"0 0 12 * * *"
    public void verifyBookStatus() {
        List<Assignments> waitingAssignments = assignmentsRepository.getAllActiveAndWaitingAssignments();
        List<Book> books = bookRepository.findAll();

        books
                .stream()
                .filter(book -> waitingAssignments
                        .stream()
                        .noneMatch(assignments -> assignments.getBook().getId() == book.getId())).collect(Collectors.toList())
                .stream()
                .forEach(book ->
                        book.setBookStatus(StatusType.FREE));
        bookRepository.saveAll(books);
    }
}