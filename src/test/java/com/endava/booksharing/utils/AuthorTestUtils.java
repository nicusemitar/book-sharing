package com.endava.booksharing.utils;

import com.endava.booksharing.model.Author;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;

import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_FIRST_NAME_TWO;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME_ONE;
import static com.endava.booksharing.TestConstants.AUTHOR_LAST_NAME_TWO;
import static com.endava.booksharing.TestConstants.ID_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorTestUtils {
    public static final Author AUTHOR_ONE = Author.builder()
            .id(ID_ONE)
            .firstName(AUTHOR_FIRST_NAME_ONE)
            .lastName(AUTHOR_LAST_NAME_ONE)
            .books(Collections.EMPTY_SET)
            .build();

    public static final Author AUTHOR_TWO = Author.builder()
            .id(ID_ONE)
            .firstName(AUTHOR_FIRST_NAME_TWO)
            .lastName(AUTHOR_LAST_NAME_TWO)
            .books(Collections.EMPTY_SET)
            .build();
}
