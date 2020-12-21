package com.endava.booksharing.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-403";
            }
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() || statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()
                    || statusCode == HttpStatus.BAD_REQUEST.value() || statusCode == HttpStatus.BAD_GATEWAY.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
