package com.endava.booksharing.utils.specifications;

import com.endava.booksharing.model.Book;
import com.endava.booksharing.model.enums.StatusType;
import com.endava.booksharing.model.enums.TagsType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookSpec {

    public static Specification<Book> hasTitle(String find) {
        return (Specification<Book>) (root, cq, cb) ->
                cb.like(root.get("title"), "%" + find + "%");
    }

    public static Specification<Book> isNotDeleted() {
        return (book, cq, cb) ->
                cb.isNull(book.get("deletedAt"));
    }

    public static Specification<Book> hasTag(String tag) {
        return (book, cq, cb) ->
                cb.equal(book.join("tags").get("tagName"), tag);
    }

    public static Specification<Book> filter(String authorName, String language, Set<String> tags, String tagsFind, StatusType status) {
        return (book, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!authorName.isEmpty()) {
                predicates.add(cb.or(cb.like(book.join("author").get("firstName"), "%" + authorName + "%"),
                        cb.like(book.join("author").get("lastName"), "%" + authorName + "%")));
            }
            if (!language.isEmpty()) {
                predicates.add(cb.equal(book.get("bookLanguage"), language));
            }
            if (tags != null) {
                for (String tag : tags) {
                    predicates.add(cb.equal(book.join("tags").get("tagName"), tag));
                }
            }
            if (!tagsFind.isEmpty()) {
                predicates.add(
                        cb.and(cb.equal(book.join("tags").get("tagName"), tagsFind),
                                book.join("tags").get("tagType").in(TagsType.CUSTOM)));
            }
            if (status != null) {
                predicates.add(book.get("bookStatus").in(status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
