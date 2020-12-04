package com.endava.booksharing.repository;

import com.endava.booksharing.model.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentsRepository extends JpaRepository<Assignments, Long> {
    List<Assignments> getAssignmentsByUserId(Long id);
}
