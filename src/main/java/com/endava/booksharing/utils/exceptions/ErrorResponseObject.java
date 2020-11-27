package com.endava.booksharing.utils.exceptions;

import java.util.Date;

public class ErrorResponseObject {
    private String message;
    private Date time;

    public ErrorResponseObject(String message) {
        this.message = message;
        this.time = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }
}
