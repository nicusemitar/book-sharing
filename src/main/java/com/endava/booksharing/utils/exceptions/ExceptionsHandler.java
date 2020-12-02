package com.endava.booksharing.utils.exceptions;

import com.endava.booksharing.api.exchange.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
    @ExceptionHandler(value = {UserCredentialsExceptions.class})
    public ResponseEntity<Object> handleCustomExceptions(Exception exception) {
        return ResponseEntity.badRequest().body(Response.build(exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleUnknownException(Exception ex) {
        log.warn("Instance not saved!");
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage()), METHOD_NOT_ALLOWED);
        } else if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(new ErrorResponseObject("A null value has created an issue, the request could not be performed"), BAD_REQUEST);
        } else if (ex instanceof MethodArgumentNotValidException) {
            return ResponseEntity.badRequest().body(Response.build("Field: " + ((MethodArgumentNotValidException) ex).getFieldError().getField() + ";  Message: " + ((MethodArgumentNotValidException) ex).getFieldError().getDefaultMessage()));
        }
        return new ResponseEntity<>(new ErrorResponseObject(ex.getMessage()), BAD_GATEWAY);
    }
}
