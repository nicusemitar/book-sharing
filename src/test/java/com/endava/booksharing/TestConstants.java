package com.endava.booksharing;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {
    public static final long ID_ONE = 1L;
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
    public static final Long USER_ID = 1l;
}
