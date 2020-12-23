package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.AssignmentsResponseDto;
import com.endava.booksharing.api.dto.TimeExtendRequestDto;
import com.endava.booksharing.api.dto.TimeExtendResponseDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.AssignmentsService;
import com.endava.booksharing.service.TimeExtendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentsRestController {

    private final AssignmentsService assignmentsService;
    private final TimeExtendService timeExtendService;

    @GetMapping(value = "/current-user")
    public ResponseEntity<Object> getAssignmentsByLoggedUser() {
        return new ResponseEntity<>(assignmentsService.getAssignmentsByUser(), HttpStatus.OK);
    }

    @PostMapping("/{id}/extends")
    public ResponseEntity<Response<TimeExtendResponseDto>> saveRequest(@PathVariable Long id,
                                                                       @RequestBody @Valid TimeExtendRequestDto timeExtendRequestDto) {
        return ResponseEntity.ok(Response.build(timeExtendService.save(timeExtendRequestDto, id)));
    }

    @DeleteMapping("/extends/{id}")
    public ResponseEntity<Object> deleteRequest(@PathVariable Long id) {
        timeExtendService.deleteRequest(id);
        return ResponseEntity.ok(Response.build("Request with id " + id + " was declined"));
    }

    @GetMapping("/extends")
    public ResponseEntity<Object> getAllRequests(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "8", name = "size") int size) {
        if (page < 0 || size <= 0) throw new IllegalArgumentException("Illegal arguments");
        return ResponseEntity.ok(timeExtendService.getAllRequests(page, size));
    }

    @PostMapping(value = "/{bookId}/assign")
    public ResponseEntity<AssignmentsResponseDto> assignToCurrentUser(@PathVariable @NotNull Long bookId) {
        return ResponseEntity.ok(assignmentsService.saveAssigmentForCurrentUserForBookWithID(bookId));
    }
}
