package com.abcbank.api.account;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccountAccessDeniedException.class)
    public ProblemDetail handleForbidden() {
        return problem(HttpStatus.FORBIDDEN, "The user is not authorized to access this account.");
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleNotFound() {
        return problem(HttpStatus.NOT_FOUND, "The requested account was not found.");
    }

    @ExceptionHandler
    public ProblemDetail handleValidation(Exception exception) {
        return problem(HttpStatus.BAD_REQUEST, "The request contains invalid or missing parameters.");
    }

    private ProblemDetail problem(HttpStatus status, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}