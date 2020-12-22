package com.endava.booksharing.repository;

import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.enums.StatusType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Override
    @EntityGraph(attributePaths = {"tags", "author"})
    Optional<Book> findById(Long aLong);

    Optional<Book> findByIdAndBookStatusIsNot(Long id, StatusType statusType);

    @Query(value = "SELECT DISTINCT book_language FROM t_book", nativeQuery = true )
    List<Object> findBookLanguage();
}
