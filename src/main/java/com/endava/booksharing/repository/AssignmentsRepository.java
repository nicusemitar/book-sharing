package com.endava.booksharing.repository;

import com.endava.booksharing.model.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentsRepository extends JpaRepository<Assignments, Long> {
    List<Assignments> getAssignmentsByUserId(Long id);
    List<Assignments> getAllByBookId(Long bookID);

    @Query("SELECT a from Assignments a where a.assignDate >= CURRENT_DATE")
    List<Assignments> getAllActiveAndWaitingAssignments();
}
