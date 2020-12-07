package com.endava.booksharing.repository;

import com.endava.booksharing.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("FROM Review r JOIN r.book b WHERE b.id = :bookId ORDER BY r.id DESC")
    List<Review> getAllReviewsByBookIdDesc(@Param("bookId") Long bookId);
}
