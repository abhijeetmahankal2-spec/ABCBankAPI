package com.abcbank.api.account;

import java.math.BigDecimal;

public record AccountInformation(
        String accountNumber,
        String userId,
        String accountHolderName,
        String accountType,
        String currency,
        BigDecimal availableBalance,
        String accountStatus,
        String branchCode,
        String ifscCode) {
}