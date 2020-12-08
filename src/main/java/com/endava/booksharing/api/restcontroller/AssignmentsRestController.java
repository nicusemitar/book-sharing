package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.api.dto.TimeExtendRequestDto;
import com.endava.booksharing.api.dto.TimeExtendResponseDto;
import com.endava.booksharing.api.exchange.Response;
import com.endava.booksharing.service.AssignmentsService;
import com.endava.booksharing.service.TimeExtendService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentsRestController {

    private final AssignmentsService assignmentsService;
    private final UserDetailsServiceImpl userDetailsService;
    private final TimeExtendService timeExtendService;

    @GetMapping(value = "/current-user")
    public ResponseEntity<Object> getAssignmentsByLoggedUser() {
        return new ResponseEntity<>(assignmentsService.getAssignmentsByUserId(userDetailsService.getCurrentUser().getId()), HttpStatus.OK);
    }

    @PostMapping("/{id}/extends")
    public ResponseEntity<Response<TimeExtendResponseDto>> saveRequest(@PathVariable Long id,
            @RequestBody @Valid TimeExtendRequestDto timeExtendRequestDto) {
        return ResponseEntity.ok(Response.build(timeExtendService.save(timeExtendRequestDto, id)));
    }
}
