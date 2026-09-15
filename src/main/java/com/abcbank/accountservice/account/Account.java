package com.abcbank.accountservice.account;

import java.math.BigDecimal;

public record Account(
        String accountNumber,
        String userId,
        String accountHolderName,
        String accountType,
        BigDecimal balance,
        String currency,
        String status) {
}
