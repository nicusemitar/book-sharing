package com.endava.booksharing.utils.specifications;

import com.endava.booksharing.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpec {

    public static Specification<Book> bookHasTitleLikeSpec(String find){
        return (Specification<Book>) (root, cq, cb) ->
                cb.and(cb.isNull(root.get("deletedAt")),
                        cb.like(root.get("title"), "%" + find + "%"));
    }
}
