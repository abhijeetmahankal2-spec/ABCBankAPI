package com.abcbank.account.model;

import java.time.Instant;

public record ErrorResponse(String errorCode, String message, Instant timestamp) {
}