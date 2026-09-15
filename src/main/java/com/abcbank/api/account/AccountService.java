package com.abcbank.api.account;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final Map<String, AccountInformation> accounts = new ConcurrentHashMap<>();

    public AccountService() {
        accounts.put("123456789012", new AccountInformation(
                "123456789012", "USR100245", "John Doe", "SAVINGS", "INR",
                new BigDecimal("125000.50"), "ACTIVE", "NAG001", "BANK0001234"));
    }

    public AccountInformation getAccount(String accountNumber, String userId) {
        AccountInformation account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        if (!account.userId().equals(userId)) {
            throw new AccountAccessDeniedException();
        }
        return account;
    }
}