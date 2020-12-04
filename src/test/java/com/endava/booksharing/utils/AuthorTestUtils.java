package com.endava.booksharing.utils;

import com.endava.booksharing.model.Author;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorTestUtils {
    public static final Author AUTHOR_ONE = Author.builder()
            .firstName(AUTHOR_FIRST_NAME)
            .lastName(AUTHOR_LAST_NAME)
            .build();
}
