package com.endava.booksharing.api.restcontroller;

import com.endava.booksharing.service.AssignmentsService;
import com.endava.booksharing.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentsRestController {

    private final AssignmentsService assignmentsService;
    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping(value = "/current-user")
    public ResponseEntity<Object> getAssignmentsByLoggedUser() {
        return new ResponseEntity<>(assignmentsService.getAssignmentsByUserId(userDetailsService.getCurrentUser().getId()), HttpStatus.OK);
    }
}
