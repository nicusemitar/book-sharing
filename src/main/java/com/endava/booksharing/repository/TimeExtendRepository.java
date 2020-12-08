package com.endava.booksharing.repository;

import com.endava.booksharing.model.TimeExtend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeExtendRepository extends JpaRepository<TimeExtend, Long> {
    Optional<TimeExtend> findByAssignmentId(Long id);
}
