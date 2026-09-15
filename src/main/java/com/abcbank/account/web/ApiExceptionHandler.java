package com.abcbank.account.web;

import com.abcbank.account.model.ErrorResponse;
import com.abcbank.account.service.AccountNotFoundException;
import com.abcbank.account.service.ForbiddenException;
import com.abcbank.account.service.InvalidRequestException;
import com.abcbank.account.service.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest() {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "Invalid account number or user ID.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter() {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "Invalid account number or user ID.");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthenticated() {
        return error(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Authentication is required.");
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden() {
        return error(HttpStatus.FORBIDDEN, "FORBIDDEN", "User is not authorized to access this account.");
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound() {
        return error(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND", "Account not found.");
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(code, message, Instant.now()));
    }
}