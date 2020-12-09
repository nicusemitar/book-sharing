package com.endava.booksharing.utils.exceptions;

public class UserAlreadyHaveAssignment extends RuntimeException {
    public UserAlreadyHaveAssignment(String message) {
        super(message);
    }
}