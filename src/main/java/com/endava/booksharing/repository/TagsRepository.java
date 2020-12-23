package com.endava.booksharing.repository;

import com.endava.booksharing.model.Tags;
import com.endava.booksharing.model.enums.TagsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {
    List<Tags> findAllByTagTypeEquals(TagsType tagType);
    List<Tags> findAllByTagNameContaining(String tagName);
    Optional<Tags> findTagsByTagName(String tagName);
}
