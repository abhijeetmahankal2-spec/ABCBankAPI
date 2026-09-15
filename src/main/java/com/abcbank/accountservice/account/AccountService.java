package com.abcbank.accountservice.account;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    private final Map<String, Account> accounts = Map.of(
            "1234567890", new Account("1234567890", "user-1001", "Alex Morgan", "CHECKING",
                    new BigDecimal("1250.75"), "USD", "ACTIVE"),
            "9876543210", new Account("9876543210", "user-2002", "Jordan Lee", "SAVINGS",
                    new BigDecimal("8400.00"), "USD", "ACTIVE"));

    public Account findAuthorizedAccount(String accountNumber, String userId) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        if (!account.userId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account access is not authorized");
        }
        return account;
    }
}
