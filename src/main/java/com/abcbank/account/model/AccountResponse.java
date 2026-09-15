package com.abcbank.account.model;

import java.math.BigDecimal;

public record AccountResponse(
        String accountNumber,
        String userId,
        String accountHolderName,
        AccountType accountType,
        String currency,
        BigDecimal availableBalance,
        AccountStatus accountStatus,
        String branchCode,
        String ifscCode) {
}