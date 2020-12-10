package com.endava.booksharing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {

    public static final Long ID_ONE = 1L;
    public static final Long ID_TWO = 2L;
    public static final LocalDate ASSIGN_DATE = LocalDate.of(2020, 9, 3);
    public static final LocalDate DUE_DATE = LocalDate.of(2020, 9, 13);
    public static final String DUE_DATE_AS_STRING = "2020-09-13";

    public static final String BOOK_TITLE_ONE = "Frankenstein-Test";
    public static final String BOOK_TITLE_TWO = "Crime and punishment";
    public static final String AUTHOR_FIRST_NAME_ONE = "Lev";
    public static final String AUTHOR_FIRST_NAME_TWO = "Fyodor";
    public static final String AUTHOR_LAST_NAME_ONE = "Tolstoy";
    public static final String AUTHOR_LAST_NAME_TWO = "Dostoevsky";
    public static final String AUTHOR_FULL_NAME_ONE = "Lev Tolstoy";
    public static final String AUTHOR_FULL_NAME_TWO = "Fyodor Dostoevsky";
    public static final String RUSSIAN_LANGUAGE = "russian";
    public static final Long BOOK_PAGES_NUMBER_ONE = 500L;
    public static final LocalDate ADDED_AT_DATE = LocalDate.of(2020, 1, 1);

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
    public static final String USER_TWO_USERNAME = "usernametwo";
    public static final String USER_TWO_PASSWORD = "usertwopass";
    public static final Long USER_ID = 1L;

    public static final String REVIEW_TEXT_CORRECT = "Good book";

    public static final String BOOK_ADDED_AT = "2020-02-12";
    public static final LocalDate BOOK_ADDED_AT_DATE = LocalDate.of(2020, 2, 12);
    public static final String BOOK_LANGUAGE = "English";
    public static final String BOOK_DELETED_AT = "2020-08-12";
    public static final LocalDate BOOK_DELETED_AT_DATE = LocalDate.of(2020, 8, 12);
    public static final String BOOK_DELETED_WHY = "Forbidden content";

    public static final String TAG_ONE = "science";
    public static final String AUTHOR_FIRST_NAME = "Harry";
    public static final String AUTHOR_LAST_NAME = "Potter";
    public static final String BOOK_TITLE = "Nirvana";
    public static final String BOOK_TITLE_UPDATED = "Nirvana Updated";
    public static final Long BOOK_PAGES = 120L;
    public static final String BOOK_DESCRIPTION = "This is the best book I've read in my life!";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020,12,1);

    public static final String TIME_EXTEND_DESCRIPTION = "I need one more week";
    public static final LocalDate TIME_EXTEND_DATE_VALID = LocalDate.of(2020, 9, 15);
    public static final String TIME_EXTEND_DATE_VALID_AS_STRING = "2020-09-15";
    public static final String TIME_EXTEND_DATE_VALID_REQUEST_AS_STRING = "2020-9-15";
    public static final String TIME_EXTEND_DATE_EQUAL_WITH_DUE_DATE_AS_STRING = "2020-9-13";
    public static final String TIME_EXTEND_DATE_SMALLER_THAN_DUE_DATE_AS_STRING = "2020-9-5";
    public static final String TIME_EXTEND_DATE_GREATER_THAN_ASSIGN_DATE_PLUS_30_DAYS_AS_STRING = "2020-10-3";
}