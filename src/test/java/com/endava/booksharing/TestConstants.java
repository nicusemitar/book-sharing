package com.endava.booksharing;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {

    public static final long ID_ONE = 1L;
    public static final LocalDate ASSIGN_DATE = LocalDate.of(2020, 9, 3);
    public static final LocalDate DUE_DATE = LocalDate.of(2020, 9, 13);
    public static final String ASSIGN_DATE_AS_STRING = "2020-09-03";
    public static final String DUE_DATE_AS_STRING = "2020-09-13";
    public static final String BOOK_TITLE_ONE = "Frankenstein-Test";

    public static final long ID_TWO = 2L;
    public static final String USERNAME_FAIL = "..endava";
    public static final String USERNAME_PASS = "endava2";
    public static final String EMAIL_PASS = "test@endava.com";
    public static final String EMAIL_FAIL = "test@endavacom";
    public static final String PASSWORD_PASS = "test12345";
    public static final String PASSWORD_PASS_ENCODED = "$2y$12$kmD6iVD/CFMYDFhANxNRZuBlFI/SsWMMWQASnOunsLX9EX.WjpWiS";
    public static final String PASSWORD_FAIL = "test";
    public static final String USER_ONE_PASSWORD_ENCODED = "$2a$10$iboRMYI5B8PR4t0Y2ldWguGuQ4kxStmQwgfKhOruroj.oJvoeoWW6";
    public static final String USER_ONE_USERNAME = "usernameone";
    public static final String USER_ONE_PASSWORD = "useronepass";
    public static final Long USER_ID = 1L;
    public static final String TAG_ONE = "science";
    public static final String AUTHOR_FIRST_NAME = "Harry";
    public static final String AUTHOR_LAST_NAME = "Potter";
    public static final String BOOK_TITLE = "Nirvana";
    public static final String BOOK_TITLE_UPDATED = "Nirvana Updated";
    public static final String BOOK_LANGUAGE = "English";
    public static final long BOOK_PAGES = 120;
    public static final String BOOK_DESCRIPTION = "This is the best book I've read in my life!";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020,12,1);
}
